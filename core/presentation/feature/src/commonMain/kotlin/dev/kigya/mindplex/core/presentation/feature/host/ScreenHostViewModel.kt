package dev.kigya.mindplex.core.presentation.feature.host

import androidx.lifecycle.ViewModel
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract

class ScreenHostViewModel(appNavigatorContract: AppNavigatorContract) : ViewModel() {
    val navigationChannel = appNavigatorContract.navigationChannel
}
