import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.main.presentation.compose.InternalMainContent

@Composable
fun MainContent(
    component: MainComponent,
) {
    InternalMainContent(
        state = component.state.subscribeAsState(),
        intentProcessor = { intent -> component.processAction(intent) }
    )
}