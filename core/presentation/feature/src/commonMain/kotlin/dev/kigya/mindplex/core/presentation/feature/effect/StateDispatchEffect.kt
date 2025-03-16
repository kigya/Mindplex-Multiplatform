package dev.kigya.mindplex.core.presentation.feature.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.kigya.mindplex.core.presentation.common.extension.collectAsPlatformState
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract

@Composable
inline fun <reified STATE, EVENT, EFFECT> use(
    component: UnidirectionalViewModelContract<STATE, EVENT, EFFECT>,
): StateDispatchEffect<STATE, EVENT, EFFECT> {
    val state by component.state.collectAsPlatformState()
    val dispatch: (EVENT) -> Unit = component::handleEvent
    val stateDispatchEffect = remember(
        key1 = state,
        key2 = component.effect,
        key3 = dispatch,
    ) {
        StateDispatchEffect(
            state = state,
            effectFlow = StableFlow(component.effect),
            dispatch = dispatch,
        )
    }

    return stateDispatchEffect
}

data class StateDispatchEffect<STATE, EVENT, EFFECT>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit,
    val effectFlow: StableFlow<EFFECT>,
)
