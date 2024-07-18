import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

sealed class Language(val isoFormat: String) {
    data object English : Language("en")
    data object French : Language("fr")
}

val LocalLocalization = staticCompositionLocalOf { Language.French.isoFormat }

@Composable
fun LocalizedApp(language: String = Language.French.isoFormat, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLocalization provides language) {
        content()
    }
}