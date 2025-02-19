package dev.kigya.mindplex.core.presentation.uikit.preview.factory

import androidx.compose.ui.tooling.preview.Devices.DESKTOP
import androidx.compose.ui.tooling.preview.Devices.FOLDABLE
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Preview
@Preview(
    name = "Phone",
    device = PHONE,
)
@Preview(
    name = "Phone",
    device = "id:Nexus 5X",
)
@Preview(
    name = "Unfolded Foldable",
    device = FOLDABLE,
)
@Preview(
    name = "Tablet",
    device = TABLET,
)
@Preview(
    name = "Desktop",
    device = DESKTOP,
)
@PreviewFontScale
@PreviewLightDark
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class PreviewScreensFactory

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@PreviewFontScale
@PreviewLightDark
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class PreviewUiKitFactory
