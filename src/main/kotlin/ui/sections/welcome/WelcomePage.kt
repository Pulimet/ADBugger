package ui.sections.welcome

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.theme.Dimensions
import ui.widgets.CardX

@Composable
fun WelcomePage(modifier: Modifier = Modifier) {
    val stateVertical = rememberScrollState(0)

    CardX(modifier = modifier) {
        Box {
            Column(
                modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()
                    .verticalScroll(stateVertical)
            ) {
                WelcomeAndDescription()
                WelcomeReleaseNotes()
            }
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight().align(Alignment.TopEnd),
                adapter = rememberScrollbarAdapter(
                    scrollState = stateVertical
                )
            )
        }
    }
}