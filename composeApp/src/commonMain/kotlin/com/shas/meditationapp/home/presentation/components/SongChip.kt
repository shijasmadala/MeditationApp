package com.shas.meditationapp.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
fun Loader() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator()
    }
}