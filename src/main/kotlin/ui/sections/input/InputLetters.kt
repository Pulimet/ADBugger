package ui.sections.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.*
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun InputLetters(coroutineScope: CoroutineScope) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
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
                Key(coroutineScope, TablerIcons.LetterQ, "Q")
                Key(coroutineScope, TablerIcons.LetterW, "W")
                Key(coroutineScope, TablerIcons.LetterE, "E")
                Key(coroutineScope, TablerIcons.LetterR, "R")
                Key(coroutineScope, TablerIcons.LetterT, "T")
                Key(coroutineScope, TablerIcons.LetterY, "Y")
                Key(coroutineScope, TablerIcons.LetterU, "U")
                Key(coroutineScope, TablerIcons.LetterI, "I")
                Key(coroutineScope, TablerIcons.LetterO, "O")
                Key(coroutineScope, TablerIcons.LetterP, "P")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Key(coroutineScope, TablerIcons.LetterA, "A")
                Key(coroutineScope, TablerIcons.LetterS, "S")
                Key(coroutineScope, TablerIcons.LetterD, "D")
                Key(coroutineScope, TablerIcons.LetterF, "F")
                Key(coroutineScope, TablerIcons.LetterG, "G")
                Key(coroutineScope, TablerIcons.LetterH, "H")
                Key(coroutineScope, TablerIcons.LetterJ, "J")
                Key(coroutineScope, TablerIcons.LetterK, "K")
                Key(coroutineScope, TablerIcons.LetterL, "L")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Key(coroutineScope, TablerIcons.LetterZ, "Z")
                Key(coroutineScope, TablerIcons.LetterX, "X")
                Key(coroutineScope, TablerIcons.LetterC, "C")
                Key(coroutineScope, TablerIcons.LetterV, "V")
                Key(coroutineScope, TablerIcons.LetterB, "B")
                Key(coroutineScope, TablerIcons.LetterN, "N")
                Key(coroutineScope, TablerIcons.LetterM, "M")
            }
        }
    }
}

@Composable
private fun Key(coroutineScope: CoroutineScope, icon: ImageVector, letter: String, model: AppStore = koinInject()) {
    BtnIcon(
        icon = icon,
        modifier = Modifier.padding(horizontal = 1.dp),
        onClick = { model.onLetterClick(coroutineScope, letter) },
        description = letter,
        showTooltip = false
    )
}
