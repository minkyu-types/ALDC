package dev.loki.ask.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.loki.ask.theme.Constraint
import dev.loki.ask.theme.OnPoint

/**
 * 체크박스를 통해 아이템(들) 선택 여부를 변경할 수 있는 아이템
 */
@Composable
fun <T> SelectionModeItem(
    item: T,
    isChecked: Boolean,
    onCheckedChange: (T, Boolean) -> Unit,
//    checkedBoxColor: Color = ConstraintLight,
//    uncheckedBoxColor: Color = OnPrimaryContainerLight,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked ->
                onCheckedChange(item, isChecked)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = OnPoint,
                uncheckedColor = Constraint
            ),
            modifier = Modifier
                .padding(end = 8.dp)
        )
        content()
        Spacer(modifier = Modifier.padding(end = 8.dp))
    }
}