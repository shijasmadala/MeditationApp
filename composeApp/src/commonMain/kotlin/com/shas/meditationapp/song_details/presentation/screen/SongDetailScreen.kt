import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shas.meditationapp.ui.theme.AppBackground
import meditationapp.composeapp.generated.resources.Res
import meditationapp.composeapp.generated.resources.nature
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongDetailsScreen(
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
    ) {

        // 🔝 Top Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Now Playing",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppBackground,
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.Unspecified,
                titleContentColor = Color.Unspecified,
                actionIconContentColor = Color.Unspecified
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🖼 Album Art
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.DarkGray)
        ) {
            Image(
                painter = painterResource(Res.drawable.nature),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🎵 Title
        Text(
            text = "Sleep Essentials - Featured",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 🧘 Subtitle
        Text(
            text = "Meditation Session",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 📊 Plays + Duration
        Text(
            text = "500K plays  •  30:00",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ⏳ Slider
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Slider(
                value = 0f,
                onValueChange = {},
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.DarkGray
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("0:00", color = Color.Gray, fontSize = 12.sp)
                Text("30:00", color = Color.Gray, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ▶ Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { }, colors = IconButtonColors(
                    containerColor = Color.DarkGray.copy(alpha = 0.3f),
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(26.dp)
                )
            }

            // Play button
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8A2BE2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            IconButton(
                onClick = { }, colors = IconButtonColors(
                    containerColor = Color.DarkGray.copy(alpha = 0.3f),
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        //description
        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
                .fillMaxWidth().height(200.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.DarkGray.copy(alpha = 0.6f)).padding(8.dp)
        ) {
            Column {
                Text(
                    "About this track",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyMedium,
                    text = "This meditation session is designed to help you find peace and tranquility. Perfect for daily meditation practice. Close your eyes, breathe deeply, and let the soothing sounds guide you to a state of calm."
                )
            }
        }
    }
}
