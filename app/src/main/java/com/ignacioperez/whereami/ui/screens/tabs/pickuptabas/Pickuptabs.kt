import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ignacioperez.whereami.R
import com.ignacioperez.whereami.ui.screens.Information

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Info)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Information",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Information(R.string.pickups_info)
            Information(R.string.chests_info)
            Information(R.string.pills_info)
            Information(R.string.cards_rune_info)

            Column(verticalArrangement = Arrangement.Center) {
                Information(R.string.rune_lookLike)
                Spacer(Modifier.size(width = 15.dp, height = 0.dp))

                Image(
                    painter = painterResource(R.drawable.rune2),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            }
            Column(verticalArrangement = Arrangement.Center) {
                Information(R.string.inverseCardlookLike)
                Spacer(Modifier.size(width = 15.dp, height = 0.dp))

                Image(
                    painter = painterResource(R.drawable.reversetarotcard),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            }
            Column(verticalArrangement = Arrangement.Center) {
                Information(R.string.card_lookLike)
                Spacer(Modifier.size(width = 15.dp, height = 0.dp))
                Image(
                    painter = painterResource(R.drawable.tarotcard),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

object FavTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Fav",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Yellow), contentAlignment = Alignment.Center
        ) {
            Text("FavScreen", fontSize = 22.sp, color = Color.Black)
        }
    }
}

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Menu)
            return remember {
                TabOptions(
                    index = 2u,
                    title = "Profile",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Gray), contentAlignment = Alignment.Center
        ) {
            Text("ProfileScreen", fontSize = 22.sp, color = Color.White)
        }
    }
}