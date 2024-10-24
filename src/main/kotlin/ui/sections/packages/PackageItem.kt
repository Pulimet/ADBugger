package ui.sections.packages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Package
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.packages_btn_add_to_favorites
import net.alexandroid.adbugger.adbugger.generated.resources.packages_btn_remove_from_favorites
import org.jetbrains.compose.resources.stringResource
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.buttons.BtnIcon

@Composable
fun PackageItem(
    item: Package,
    isSelected: Boolean,
    onClicked: (pckg: Package) -> Unit,
    addToFavoritesEnabled: Boolean = true,
    removeFromFavoritesEnabled: Boolean = false,
    modifier: Modifier = Modifier,
    onAddToFavorites: (pckg: Package) -> Unit = {},
    onRemoveFromFavorites: (pckg: Package) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClicked(item) })
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onClicked(item) },
            colors = RadioButtonDefaults.colors(
                selectedColor = MyColors.radioButtonSelected,
                unselectedColor = MyColors.radioButtonUnselected
            )
        )
        Text(
            text = item.name,
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 6.dp).weight(1f)
        )

        if (addToFavoritesEnabled) {
            BtnIcon(
                icon = Icons.Rounded.Star,
                modifier = Modifier.padding(horizontal = 16.dp),
                description = stringResource(Res.string.packages_btn_add_to_favorites),
                buttonSize = Dimensions.btnSizeSmall,
                iconSize = Dimensions.btnIconSizeSmall,
                showTooltip = false,
                clickEffect = true,
                hoverEnabled = true,
                onClick = { onAddToFavorites(item) }
            )
        }

        if (removeFromFavoritesEnabled) {
            BtnIcon(
                icon = Icons.Rounded.Delete,
                modifier = Modifier.padding(horizontal = 16.dp),
                description = stringResource(Res.string.packages_btn_remove_from_favorites),
                buttonSize = Dimensions.btnSizeSmall,
                iconSize = Dimensions.btnIconSizeSmall,
                showTooltip = false,
                clickEffect = true,
                hoverEnabled = true,
                onClick = { onRemoveFromFavorites(item) }
            )
        }
    }
}

