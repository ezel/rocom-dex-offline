package org.martin.rocomdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
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
    var currentDestination by rememberSaveable { mutableStateOf(RouteDestinations.HOME) }
    val backStack = remember { mutableStateListOf<Any>(RouteSearch) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            RouteDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { backStack.add(it.route) }
                )
            }
        }
    ) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<RouteSearch> {
                    HomeScreen()
                }
                entry<RoutePets> {
                    PetsScreen()
                }
                entry<RouteSkills> {
                    SkillsScreen()
                }
                entry<RouteTags> {
                    PetsScreen()
                }
                entry<RouteProfile> {
                    PetsScreen()
                }
            }
        )
    }
}
