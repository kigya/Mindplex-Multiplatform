package dev.kigya.mindplex.core.util.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.compose.currentKoinScope
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel { scope.get<T>() }
}