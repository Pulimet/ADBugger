package ui.sections

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import compose.icons.Octicons
import compose.icons.TablerIcons
import compose.icons.octicons.ArrowDown24
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.ArrowRight24
import compose.icons.octicons.ArrowUp24
import compose.icons.tablericons.*
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
@Preview
fun Keyboard(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    Column {
        Letters(model, coroutineScope)
        Numbers(model, coroutineScope)
        Arrows(model, coroutineScope)
        SendInput(model, coroutineScope)
    }
}

@Composable
fun Letters(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(
            start = Dimensions.selectedPagePadding,
            end = Dimensions.selectedPagePadding,
            top = Dimensions.selectedPagePadding,
            bottom = 8.dp
        ),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Key(model, coroutineScope, TablerIcons.LetterQ, "Q")
                Key(model, coroutineScope, TablerIcons.LetterW, "W")
                Key(model, coroutineScope, TablerIcons.LetterE, "E")
                Key(model, coroutineScope, TablerIcons.LetterR, "R")
                Key(model, coroutineScope, TablerIcons.LetterT, "T")
                Key(model, coroutineScope, TablerIcons.LetterY, "Y")
                Key(model, coroutineScope, TablerIcons.LetterU, "U")
                Key(model, coroutineScope, TablerIcons.LetterI, "I")
                Key(model, coroutineScope, TablerIcons.LetterO, "O")
                Key(model, coroutineScope, TablerIcons.LetterP, "P")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Key(model, coroutineScope, TablerIcons.LetterA, "A")
                Key(model, coroutineScope, TablerIcons.LetterS, "S")
                Key(model, coroutineScope, TablerIcons.LetterD, "D")
                Key(model, coroutineScope, TablerIcons.LetterF, "F")
                Key(model, coroutineScope, TablerIcons.LetterG, "G")
                Key(model, coroutineScope, TablerIcons.LetterH, "H")
                Key(model, coroutineScope, TablerIcons.LetterJ, "J")
                Key(model, coroutineScope, TablerIcons.LetterK, "K")
                Key(model, coroutineScope, TablerIcons.LetterL, "L")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Key(model, coroutineScope, TablerIcons.LetterZ, "Z")
                Key(model, coroutineScope, TablerIcons.LetterX, "X")
                Key(model, coroutineScope, TablerIcons.LetterC, "C")
                Key(model, coroutineScope, TablerIcons.LetterV, "V")
                Key(model, coroutineScope, TablerIcons.LetterB, "B")
                Key(model, coroutineScope, TablerIcons.LetterN, "N")
                Key(model, coroutineScope, TablerIcons.LetterM, "M")
            }
        }
    }

}

@Composable
fun Numbers(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = Dimensions.selectedPagePadding, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(model, coroutineScope, TablerIcons.Number1, 1)
                Num(model, coroutineScope, TablerIcons.Number2, 2)
                Num(model, coroutineScope, TablerIcons.Number3, 3)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(model, coroutineScope, TablerIcons.Number4, 4)
                Num(model, coroutineScope, TablerIcons.Number5, 5)
                Num(model, coroutineScope, TablerIcons.Number6, 6)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(model, coroutineScope, TablerIcons.Number7, 7)
                Num(model, coroutineScope, TablerIcons.Number8, 8)
                Num(model, coroutineScope, TablerIcons.Number9, 9)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(model, coroutineScope, TablerIcons.Number0, 0)
            }
        }
    }
}

@Composable
fun Arrows(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = Dimensions.selectedPagePadding, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                BtnIcon(
                    icon = Octicons.ArrowUp24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onUpClick(coroutineScope) },
                    description = "Up"
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                BtnIcon(
                    icon = Octicons.ArrowLeft24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onLeftClick(coroutineScope) },
                    description = "Left"
                )
                BtnIcon(
                    icon = Octicons.ArrowDown24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onDownClick(coroutineScope) },
                    description = "Down"
                )
                BtnIcon(
                    icon = Octicons.ArrowRight24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onRightClick(coroutineScope) },
                    description = "Right"
                )
            }
        }
    }
}

@Composable
fun SendInput(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    val textInputSendTextState = remember { mutableStateOf(TextFieldValue("")) }
    val textInputSendInputState = remember { mutableStateOf(TextFieldValue("")) }
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = Dimensions.selectedPagePadding, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextField(
                    modifier = Modifier.padding(6.dp).weight(1f),
                    singleLine = true,
                    textStyle = TextStyle(color = Color.White),
                    value = textInputSendTextState.value,
                    label = { Text("Send text to device/s") },
                    onValueChange = { value -> textInputSendTextState.value = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputSendTextState.value.text.isNotEmpty(),
                    onClick = { model.onSendTextClick(coroutineScope, textInputSendTextState.value.text) },
                    description = "Send text to device"
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.ArrowBack,
                    modifier = Modifier.padding(start = 4.dp, end = 16.dp),
                    enabled = textInputSendTextState.value.text.isNotEmpty(),
                    onClick = {
                        model.onBackSpaceClick(coroutineScope)
                        val text = textInputSendTextState.value.text
                        val textLength = text.length
                        if (textLength > 0) {
                            textInputSendTextState.value = TextFieldValue(text.substring(0, textLength - 1))
                        }
                    },
                    description = "Backspace"
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextField(
                    modifier = Modifier.padding(6.dp).weight(1f),
                    singleLine = true,
                    textStyle = TextStyle(color = Color.White),
                    value = textInputSendInputState.value,
                    label = { Text("Send input/key to device/s") },
                    onValueChange = { value -> textInputSendInputState.value = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    enabled = textInputSendInputState.value.text.isNotEmpty(),
                    onClick = { model.onSendInputClick(coroutineScope, textInputSendInputState.value.text.toInt()) },
                    description = "Send input/key to device"
                )
            }
        }
    }
}


@Composable
fun Key(model: AppStore, coroutineScope: CoroutineScope, icon: ImageVector, letter: String) {
    BtnIcon(
        icon = icon,
        modifier = Modifier.padding(horizontal = 1.dp),
        onClick = { model.onLetterClick(coroutineScope, letter) },
        description = letter,
        showTooltip = false
    )
}


@Composable
fun Num(model: AppStore, coroutineScope: CoroutineScope, icon: ImageVector, i: Int) {
    BtnIcon(
        icon = icon,
        modifier = Modifier.padding(horizontal = 4.dp),
        onClick = { model.onNumberClick(coroutineScope, i) },
        description = i.toString(),
        showTooltip = false
    )
}

