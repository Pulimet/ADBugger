package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.HoverButton

@Composable
fun LogcatPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.startLogcat() },
                    enabled = true,
                    text = "Start Logcat"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.stopLogcat() },
                    enabled = true,
                    text = "Stop Logcat"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    //   adb logcat -d > [path_to_file] // Save the logcat output to a file on the local system.
                    onClick = {},
                    enabled = true,
                    text = "Save to file"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    //  adb bugreport > [path_to_file] // Will dump the whole device information like dumpstate, dumpsys and logcat output.
                    onClick = {},
                    enabled = true,
                    text = "Bugreport"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = {/* adb logcat -c */ },
                    enabled = true,
                    text = "Clear logcat"
                )
            }
        }
    }
}
