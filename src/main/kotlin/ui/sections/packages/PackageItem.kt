package ui.sections.packages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Package
import ui.theme.MyColors

@Composable
fun PackageItem(
    item: Package,
    isSelected: Boolean,
    onClicked: (device: Package) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClicked(item) })
    ) {
        RadioButton(
            selected = isSelected, onClick = { onClicked(item) }, colors = RadioButtonDefaults.colors(
                selectedColor = MyColors.radioButtonSelected,
                unselectedColor = MyColors.radioButtonUnselected
            )
        )
        Text(text = item.name, color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(bottom = 6.dp))
    }
}

