package org.martin.rocomdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import org.martin.rocomdex.ui.HomeScreen
import org.martin.rocomdex.ui.pet.PetsScreen
import org.martin.rocomdex.ui.skill.SkillsScreen
import org.martin.rocomdex.ui.theme.RocomDexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RocomDexTheme {
                RocomDexApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun RocomDexApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        when(currentDestination) {
            AppDestinations.HOME -> HomeScreen()
            AppDestinations.PETS -> PetsScreen()
            AppDestinations.SKILLS -> SkillsScreen()
            AppDestinations.FAVOURITE -> PetsScreen()
            AppDestinations.PROFILE -> PetsScreen()
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("查找", R.drawable.ic_home),
    PETS("精灵", R.drawable.ic_home),
    SKILLS("技能", R.drawable.ic_home),
    FAVOURITE("收藏", R.drawable.ic_favorite),
    PROFILE("关于", R.drawable.ic_account_box),
}