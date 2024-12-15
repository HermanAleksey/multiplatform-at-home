import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.shared.main.presentation.MainComponent

@Composable
fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.subscribeAsState()
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = modifier,
    ) {
        items(state.value.features) { featureItem ->
            if (featureItem.isEnabled) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(80.dp)
                        .background(Color.Cyan)
                        .clickable { component.onFeatureClicked(featureItem) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(featureItem.name)
                }
            }
        }
    }
}