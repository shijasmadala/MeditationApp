package com.shas.meditationapp.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.shas.meditationapp.ui.theme.AppBackground
import com.shas.meditationapp.ui.theme.FeaturedCardGradientEnd
import com.shas.meditationapp.ui.theme.FeaturedCardGradientStart
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val savedUser = state.value.savedUser

    val preferences = listOf(
        PreferenceItem("Notifications", Icons.Default.Notifications, "3"),
        PreferenceItem("Sleep Timer", Icons.Default.Nightlight, null),
        PreferenceItem("Share App", Icons.Default.Share, null),
        PreferenceItem("Settings", Icons.Default.Settings, null),
        PreferenceItem("Downloads", Icons.Default.Download, null)
    )

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier.fillMaxWidth().height(250.dp)
                .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            FeaturedCardGradientStart, FeaturedCardGradientEnd
                        )
                    )
                ), contentAlignment = Alignment.CenterStart
        ) {
            Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp).statusBarsPadding()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Profile",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(40.dp).clip(CircleShape)
                            .background(color = AppBackground)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(80.dp).clip(CircleShape).border(
                            width = 2.dp,
                            color = Color.White,
                            shape = CircleShape
                        ).background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFFA855F7),
                                    Color(0xFF3B82F6)
                                )
                            )
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = savedUser?.profileUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
//                        Text(
//                            "U",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 20.sp,
//                            color = Color.White
//                        )
                    }

                    Column {
                        Text(
                            if (savedUser?.name?.isEmpty() == true) "Guest User" else "${savedUser?.name}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Text(
                            if (savedUser?.email?.isEmpty() == true) "guest@gmail.com" else "${savedUser?.email}",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                    }

                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF081120))
                .padding(20.dp)
        ) {
            Text(
                text = "Preferences",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF111C2E)
                ),
                border = BorderStroke(
                    1.dp,
                    Color.White.copy(alpha = 0.08f)
                )
            ) {

                preferences.forEachIndexed { index, item ->

                    PreferenceRow(item)

                    if (index != preferences.lastIndex) {
                        HorizontalDivider(
                            color = Color.White.copy(alpha = 0.06f),
                            thickness = 1.dp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    width = 1.dp, color = Color.Red
                )
            ) {
                Row {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        tint = Color.Red,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Log Out",
                        color = Color.Red,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Composable
fun PreferenceRow(item: PreferenceItem) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color(0xFF5B21B6)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = Color(0xFFD8B4FE)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = item.title,
            color = Color.White,
            fontSize = 17.sp,
            modifier = Modifier.weight(1f)
        )

        item.badge?.let {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.6f)
        )
    }
}


data class PreferenceItem(
    val title: String,
    val icon: ImageVector,
    val badge: String?
)