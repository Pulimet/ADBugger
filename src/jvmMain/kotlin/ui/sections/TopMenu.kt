package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.Keyboard
import pref.preference
import store.AppStore
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun TopMenu(model: AppStore) {
    var state: Boolean by preference("Btn_KeyboardInput", false)

    LaunchedEffect(Unit) {
        model.onKeyboardInputToggle(state)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().background(MyColors.bg2)
    ) {
        BtnIcon(
            icon = FontAwesomeIcons.Regular.Keyboard,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            onClick = {
                state = !state
                model.onKeyboardInputToggle(state)
            },
            description = "Keyboard Input",
            toggle = state,
            buttonSize = 26.dp,
            iconSize = 16.dp
        )
    }
}
