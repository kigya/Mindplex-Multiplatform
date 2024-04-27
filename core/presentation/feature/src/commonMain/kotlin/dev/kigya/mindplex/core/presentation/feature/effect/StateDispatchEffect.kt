package dev.kigya.mindplex.core.presentation.feature.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.kigya.mindplex.core.presentation.feature.component.UnidirectionalComponentContract
import dev.kigya.mindplex.core.util.extension.collectAsStateMultiplatform
import kotlinx.coroutines.flow.Flow

@Composable
inline fun <reified STATE, EVENT, EFFECT> use(
    component: UnidirectionalComponentContract<STATE, EVENT, EFFECT>,
): StateDispatchEffect<STATE, EVENT, EFFECT> {
    val state by component.state.collectAsStateMultiplatform()
    val dispatch: (EVENT) -> Unit = component::handleEvent
    val stateDispatchEffect = remember(
        key1 = state,
        key2 = component.effect,
        key3 = dispatch,
    ) {
        StateDispatchEffect(
            state = state,
            effectFlow = component.effect,
            dispatch = dispatch,
        )
    }

    return stateDispatchEffect
}

data class StateDispatchEffect<STATE, EVENT, EFFECT>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit,
    @Stable val effectFlow: Flow<EFFECT>,
)
