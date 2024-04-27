package dev.kigya.mindplex.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import dev.kigya.mindplex.App
import dev.kigya.mindplex.navigation.mediator.RootComponent

/**
 * MainActivity is the entry point of the application's user interface and serves as the host
 * for the application's root UI component. It extends [ComponentActivity], which provides
 * compatibility with modern lifecycle and window management in Android.
 *
 * The main responsibilities of MainActivity include:
 * - Initializing the application's UI by setting up the root component from Decompose.
 * - Managing the lifecycle of the UI and its associated components.
 * - Configuring the window features such as edge-to-edge display for modern appearance.
 *
 * The Decompose library is used to manage the components hierarchically. This allows for a clear
 * separation of concerns and facilitates the navigation and lifecycle management of UI components
 * across the application.
 *
 * @OptIn(ExperimentalDecomposeApi::class) is used to enable usage of APIs from the Decompose
 * library that are marked as experimental. Experimental APIs in Decompose are subject to change
 * and may not be stable, but they provide advanced capabilities that are under consideration
 * for future stable releases.
 *
 * Usage:
 * - [retainedComponent] is a function from Decompose that provides a scope for retaining
 *   components during configuration changes or across the entire lifecycle of the activity.
 *   This function is used to instantiate [RootComponent], ensuring that the component
 *   survives configuration changes such as rotations.
 * - [RootComponent] is a custom component defined in the application that acts as the
 *   root of the UI component tree. It is responsible for managing the navigation and
 *   displaying content within the application. It requires a component context, which
 *   includes lifecycle and state keepers, and the app module for dependency injection.
 * - [setContent] sets the content of the activity using Jetpack Compose, where [App] is a
 *   Composable function that renders the UI defined by the root component.
 * - [enableEdgeToEdge] is a utility function defined within the activity to configure
 *   system UI flags for an edge-to-edge experience, making the app content more immersive
 *   by using the full size of the display, including areas behind system bars.
 *
 * @see ComponentActivity for more details on activity behavior and lifecycle.
 * @see RootComponent for specific details on the root component's responsibilities and behavior.
 */
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent { componentContext ->
            RootComponent(
                componentContext = componentContext,
                appModule = (application as MindplexApp).appModule,
            )
        }
        enableEdgeToEdge()
        setContent { App(root) }
    }
}
