package dev.kigya.mindplex.feature.home.presentation.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import dev.kigya.mindplex.core.presentation.feature.component.BaseComponent
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract

@Stable
class HomeComponent(
    componentContext: ComponentContext,
) : BaseComponent<HomeContract.State, HomeContract.Effect>(
    componentContext = componentContext,
    initialState = HomeContract.State(),
),
    HomeContract {

    override fun handleEvent(event: HomeContract.Event) = withUseCaseScope {
        when (event) {
            is HomeContract.Event.OnFirstLaunch -> {}
        }
    }
}
