import com.android.build.api.dsl.CommonExtension
import dev.kigya.mindplex.config.configureIfExists

configureIfExists(CommonExtension::class.java) {
    buildFeatures {
        resValues = true
    }
}
