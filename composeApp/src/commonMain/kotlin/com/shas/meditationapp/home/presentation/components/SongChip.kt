package com.shas.meditationapp.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shas.meditationapp.ui.theme.ChipActiveBackground
import com.shas.meditationapp.ui.theme.ChipInactiveBackground

@Composable
fun SongChipView(name: String?, selected: Boolean, onSelected: (String) -> Unit) {
    InputChip(
        selected = selected,
        onClick = {
            onSelected.invoke(name ?: "")
        },
        label = { Text(name.toString(), color = Color.White) },
        modifier = Modifier.padding(5.dp),
        colors = InputChipDefaults.inputChipColors(selectedContainerColor = if (selected) ChipActiveBackground else ChipInactiveBackground)
    )
}

@Composable
fun FeatureChip(
    text: String,
    icon: ImageVector
) {

    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFF160325),
        border = BorderStroke(
            1.dp,
            Color(0xFF4C1D95)
        )
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = "", tint = Color(0xFFA855F7))

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}