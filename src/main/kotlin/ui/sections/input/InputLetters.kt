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
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun InputLetters() {
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
                Key(TablerIcons.LetterQ, "Q")
                Key(TablerIcons.LetterW, "W")
                Key(TablerIcons.LetterE, "E")
                Key(TablerIcons.LetterR, "R")
                Key(TablerIcons.LetterT, "T")
                Key(TablerIcons.LetterY, "Y")
                Key(TablerIcons.LetterU, "U")
                Key(TablerIcons.LetterI, "I")
                Key(TablerIcons.LetterO, "O")
                Key(TablerIcons.LetterP, "P")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Key(TablerIcons.LetterA, "A")
                Key(TablerIcons.LetterS, "S")
                Key(TablerIcons.LetterD, "D")
                Key(TablerIcons.LetterF, "F")
                Key(TablerIcons.LetterG, "G")
                Key(TablerIcons.LetterH, "H")
                Key(TablerIcons.LetterJ, "J")
                Key(TablerIcons.LetterK, "K")
                Key(TablerIcons.LetterL, "L")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Key(TablerIcons.LetterZ, "Z")
                Key(TablerIcons.LetterX, "X")
                Key(TablerIcons.LetterC, "C")
                Key(TablerIcons.LetterV, "V")
                Key(TablerIcons.LetterB, "B")
                Key(TablerIcons.LetterN, "N")
                Key(TablerIcons.LetterM, "M")
            }
        }
    }
}

@Composable
private fun Key(icon: ImageVector, letter: String, model: AppStore = koinInject()) {
    BtnIcon(
        icon = icon,
        modifier = Modifier.padding(horizontal = 1.dp),
        onClick = { model.onLetterClick(letter) },
        description = letter,
        showTooltip = false
    )
}
