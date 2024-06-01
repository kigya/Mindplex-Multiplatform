import com.android.build.api.dsl.CommonExtension

configureIfExists(CommonExtension::class.java) {
    buildFeatures.buildConfig = true
}
