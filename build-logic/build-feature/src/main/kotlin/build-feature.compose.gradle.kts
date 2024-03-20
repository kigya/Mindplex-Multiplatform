import com.android.build.api.dsl.CommonExtension
import dev.kigya.mindplex.config.configureIfExists
import dev.kigya.mindplex.config.libs

configureIfExists(CommonExtension::class.java) {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = project.libs.versions.compose.compiler.get()
    }
}
