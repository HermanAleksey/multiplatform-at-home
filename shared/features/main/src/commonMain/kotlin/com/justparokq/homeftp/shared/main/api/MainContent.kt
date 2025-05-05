import androidx.compose.runtime.Composable
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.main.presentation.compose.InternalMainContent

@Composable
fun MainContent(
    component: MainComponent,
) {
    InternalMainContent(
        component = component,
    )
}