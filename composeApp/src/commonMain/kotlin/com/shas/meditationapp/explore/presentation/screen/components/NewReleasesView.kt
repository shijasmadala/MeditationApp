package com.shas.meditationapp.explore.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.shas.meditationapp.explore.domain.model.AlbumResultModel

@Composable
fun NewReleasesView(trackItem: AlbumResultModel?, onItemClick: (AlbumResultModel?) -> Unit) {
    Column(
        modifier = Modifier.padding(10.dp).width(110.dp).clickable { onItemClick.invoke(trackItem) },
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.Gray.copy(alpha = 0.1f))
        ) {
            AsyncImage(
                model = trackItem?.image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = trackItem?.name.orEmpty(),
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        )


        Text(
            trackItem?.releaseDate ?: "",
            color = Color.LightGray,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}