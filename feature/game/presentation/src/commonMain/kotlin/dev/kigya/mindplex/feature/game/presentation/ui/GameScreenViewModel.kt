package dev.kigya.mindplex.feature.game.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.feature.game.domain.model.GameDomainConfig
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.mindplex.feature.game.domain.model.QuestionValidationDomainModel
import dev.kigya.mindplex.feature.game.domain.model.UserChoiceDomainModel
import dev.kigya.mindplex.feature.game.domain.model.ValidationType
import dev.kigya.mindplex.feature.game.domain.usecase.GetQuestionUseCase
import dev.kigya.mindplex.feature.game.domain.usecase.GetScoreUseCase
import dev.kigya.mindplex.feature.game.domain.usecase.UpdateScoreUseCase
import dev.kigya.mindplex.feature.game.domain.usecase.ValidateQuestionUseCase
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract.Companion.QUESTION_DELAY
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract.Companion.TIME_LIMIT
import dev.kigya.mindplex.feature.game.presentation.mapper.AnswerDomainMapper
import dev.kigya.mindplex.feature.game.presentation.mapper.GameCategoryMapper
import dev.kigya.mindplex.feature.game.presentation.mapper.GameDifficultyMapper
import dev.kigya.mindplex.feature.game.presentation.mapper.GameDifficultyMapper.mappedBy
import dev.kigya.mindplex.feature.game.presentation.mapper.GameTypeMapper
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.TypePresentationModel
import dev.kigya.outcome.onSuccess
import dev.kigya.outcome.unwrap
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameScreenViewModel(
    private val getQuestionUseCase: GetQuestionUseCase,
    private val validateQuestionUseCase: ValidateQuestionUseCase,
    private val getScoreUseCase: GetScoreUseCase,
    private val updateScoreUseCase: UpdateScoreUseCase,
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<GameContract.State, GameContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = GameContract.State(),
),
    GameContract {

    private val _gameType = MutableStateFlow(TypePresentationModel.MULTIPLE)
    private var _timerJob: Job? = null

    override fun handleEvent(event: GameContract.Event) {
        withUseCaseScope {
            event.run {
                when (this) {
                    is GameContract.Event.OnFirstLaunch -> handleFirstLaunch()
                    is GameContract.Event.OnErrorStubClicked -> handleErrorStubClick()
                    is GameContract.Event.OnBackPressed -> handleBackPress()
                    is GameContract.Event.OnQuestionAnswered -> handleQuestionAnswered()
                }
            }
        }
    }

    private fun GameContract.Event.OnFirstLaunch.handleFirstLaunch() = withUseCaseScope {
        updateState { copy(type = this@handleFirstLaunch.type) }
        _gameType.update { type }
        this@handleFirstLaunch.difficulty?.let { updateState { copy(difficulty = it) } }
        this@handleFirstLaunch.category?.let { updateState { copy(category = it) } }

        getQuestion()
    }

    private fun handleErrorStubClick() = withUseCaseScope { getQuestion() }

    private fun handleBackPress() = withUseCaseScope { navigatorContract.navigateBack() }

    private fun GameContract.Event.OnQuestionAnswered.handleQuestionAnswered() = withUseCaseScope {
        validateQuestion(answerIndex)
    }

    private suspend fun getQuestion() {
        getQuestionUseCase(
            GameDomainConfig(
                category = getState().category mappedBy GameCategoryMapper,
                difficulty = getState().difficulty mappedBy GameDifficultyMapper,
                type = _gameType.value mappedBy GameTypeMapper,
            ),
        ).unwrap(
            onFailure = { error: MindplexDomainError ->
                updateState { copy(stubErrorType = error.toStubErrorType()) }
            },
            onSuccess = { questionData: QuestionDomainModel ->
                getScoreUseCase(None).onSuccess { score ->
                    updateState { copy(score = score) }
                }
                val type = questionData.config.type mappedBy GameTypeMapper
                updateState {
                    copy(
                        question = questionData.question,
                        type = type,
                        answers = questionData.answers.map { it mappedBy AnswerDomainMapper },
                        typeTitle = GameContract.State.typeTitleMap[type],
                        isLoading = false,
                        isValidatingAnswer = false,
                        currentTime = TIME_LIMIT,
                    )
                }
                startTimer()
            },
        )
    }

    private suspend fun validateQuestion(answerIndex: Int) {
        val question = requireNotNull(getState().question)

        validateQuestionUseCase(
            UserChoiceDomainModel(
                isAnswered = answerIndex != TIME_IS_UP,
                question = question,
                answerIndex = answerIndex,
            ),
        ).unwrap(
            onFailure = { error: MindplexDomainError ->
                updateState { copy(stubErrorType = error.toStubErrorType()) }
            },
            onSuccess = { questionValidation: QuestionValidationDomainModel ->
                updateScoreUseCase(questionValidation.validationType)
                val updatedAnswers = getState().answers.mapIndexed { i, answer ->
                    val domainResult = questionValidation.results.find { it.index == i }

                    val newDisplayType = when (domainResult?.validationType) {
                        ValidationType.CORRECT -> GameContract.State.Answer.DisplayType.CORRECT
                        ValidationType.INCORRECT -> GameContract.State.Answer.DisplayType.INCORRECT
                        else -> GameContract.State.Answer.DisplayType.NEUTRAL
                    }

                    answer.copy(displayType = newDisplayType)
                }

                updateState {
                    copy(
                        answers = updatedAnswers,
                        isValidatingAnswer = true,
                    )
                }

                delay(QUESTION_DELAY)
                getQuestion()
            },
        )
    }

    private fun startTimer() {
        _timerJob?.cancel()

        _timerJob = withUseCaseScope {
            launch {
                while (coroutineContext.isActive && getState().currentTime != 0) {
                    delay(TIMER_INTERVAL)
                    updateState { copy(currentTime = getState().currentTime - 1) }
                }
                validateQuestion(TIME_IS_UP)
            }
        }
    }

    private companion object {
        const val TIMER_INTERVAL = 1000L
        const val TIME_IS_UP = -1
    }
}
