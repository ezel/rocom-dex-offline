package org.martin.rocomdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.martin.rocomdex.data.DexDatabase
import org.martin.rocomdex.data.DexRepository
import org.martin.rocomdex.ui.HomeScreen
import org.martin.rocomdex.ui.pet.PetDetailScreen
import org.martin.rocomdex.ui.pet.PetsScreen
import org.martin.rocomdex.ui.pet.PetsViewModel
import org.martin.rocomdex.ui.profile.ProfileScreen
import org.martin.rocomdex.ui.profile.ProfileViewModel
import org.martin.rocomdex.ui.skill.SkillsScreen
import org.martin.rocomdex.ui.theme.RocomDexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = (application as RDApplication).database

        enableEdgeToEdge()
        setContent {
            RocomDexTheme {
                RocomDexApp(database)
            }
        }
    }
}

@Composable
fun RocomDexApp(db: DexDatabase) {
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
        val repo = DexRepository(db)

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<RouteSearch> {
                    HomeScreen()
                }
                entry<RoutePets> { key ->
                    if (key.id != null) {
                        PetDetailScreen(
                            viewModel(factory = PetsViewModel.provideFactory(repo)),
                            key.id)
                    } else {
                        PetsScreen(
                            viewModel(factory = PetsViewModel.provideFactory(repo)),
                            { id -> backStack.add(RoutePets(id)) })
                    }
                }
                entry<RouteSkills> {
                    SkillsScreen()
                }
                entry<RouteTags> {
                    SkillsScreen()
                }
                entry<RouteProfile> {
                    val repo = DexRepository(db)
                    val pvm: ProfileViewModel =
                        viewModel(factory = ProfileViewModel.provideFactory(repo))
                    ProfileScreen(pvm)
                }
            }
        )
    }
}
