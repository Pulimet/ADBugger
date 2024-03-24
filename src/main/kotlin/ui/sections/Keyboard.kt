package ui.sections

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.*
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimens
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard

@Composable
@Preview
fun Keyboard(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    ExpandableCard(
        title = "Keyboard",
        modifier = Modifier.padding(horizontal = Dimens.cardHorizontal, vertical = Dimens.cardVertical)
    ) {
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
