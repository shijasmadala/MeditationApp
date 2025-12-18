import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.shas.meditationapp.home.domain.model.ResultModel
import com.shas.meditationapp.util.UiUtils

@Composable
fun SongItemScreen(trackItem: ResultModel?, onItemClick: (ResultModel?) -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke(trackItem)
            }
            .height(110.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.Gray.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LEFT SIDE — image + text
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = trackItem?.image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(60.dp)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        trackItem?.name ?: "",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        trackItem?.releaseDate ?: "",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // PUSH RIGHT SIDE TO END
            Spacer(modifier = Modifier.weight(1f))

            // RIGHT SIDE — time + favorite icon
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.AccessTime,
                        modifier = Modifier.size(18.dp),
                        contentDescription = "",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = UiUtils.formatDuration(trackItem?.duration ?: 0),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    modifier = Modifier.size(20.dp),
                    contentDescription = "",
                    tint = Color.Gray
                )
            }
        }
    }
}
