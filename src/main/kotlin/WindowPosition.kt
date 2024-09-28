import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pref.preference
import java.awt.Dimension

var width: Float by preference("WindowWidth", 1200f)
var height: Float by preference("WindowHeight", 800f)
var x: Float by preference("WindowX", 0f)
var y: Float by preference("WindowY", 0f)

fun getPosition() = if (x != 0f && y != 0f) {
    WindowPosition(x.dp, y.dp)
} else {
    WindowPosition(alignment = Alignment.Center)
}

fun getSize() = DpSize(width.dp, height.dp)

@Composable
fun WindowPosition(windowState: WindowState) {
    LaunchedEffect(windowState) {
        snapshotFlow { windowState.size }.onEach(::onWindowResize).launchIn(this)
        snapshotFlow { windowState.position }.filter { it.isSpecified }.onEach(::onWindowRelocate)
            .launchIn(this)
    }
}

private fun onWindowResize(size: DpSize) {
    width = size.width.value
    height = size.height.value
}

private fun onWindowRelocate(it: WindowPosition) {
    x = it.x.value
    y = it.y.value
}

@Composable
fun FrameWindowScope.setMinimumSize() {
    val minWidth = 480.dp
    val minHeight = 340.dp

    val density = LocalDensity.current
    LaunchedEffect(density) {
        window.minimumSize = with(density) {
            Dimension(minWidth.toPx().toInt(), minHeight.toPx().toInt())
        }
    }
}