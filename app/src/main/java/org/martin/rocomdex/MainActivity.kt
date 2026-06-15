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
import org.martin.rocomdex.ui.pet.PetDetailViewModel
import org.martin.rocomdex.ui.pet.PetsScreen
import org.martin.rocomdex.ui.pet.PetsViewModel
import org.martin.rocomdex.ui.profile.ProfileScreen
import org.martin.rocomdex.ui.profile.ProfileViewModel
import org.martin.rocomdex.ui.skill.SkillDetailScreen
import org.martin.rocomdex.ui.skill.SkillDetailViewModel
import org.martin.rocomdex.ui.skill.SkillsScreen
import org.martin.rocomdex.ui.skill.SkillsViewModel
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
                    onClick = {
                        if (backStack.last() !== it.route) {
                            backStack.add(it.route);
                            currentDestination = it
                        }
                    }
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
                    currentDestination = RouteDestinations.HOME
                    HomeScreen()
                }
                entry<RoutePetsList> {
                    currentDestination = RouteDestinations.PETS
                    PetsScreen(
                        viewModel(factory = PetsViewModel.provideFactory(repo)),
                        { id -> backStack.add(RoutePet(id)) })
                }
                entry<RoutePet> { key ->
                    currentDestination = RouteDestinations.PETS
                    PetDetailScreen(
                        viewModel(factory = PetDetailViewModel.provideFactory(repo, key.id)),
                        key.id
                    )
                }
                entry<RouteSkillsList> {
                    currentDestination = RouteDestinations.SKILLS
                    SkillsScreen(
                        viewModel(factory = SkillsViewModel.provideFactory(repo)),
                        { id -> backStack.add(RouteSkill(id)) }
                    )
                }
                entry<RouteSkill> { key ->
                    currentDestination = RouteDestinations.SKILLS
                    SkillDetailScreen(
                        viewModel(factory = SkillDetailViewModel.provideFactory(repo, key.id)),
                        key.id
                    )
                }
                entry<RouteTags> {
                    currentDestination = RouteDestinations.FAVOURITE
                    HomeScreen()
                }
                entry<RouteProfile> {
                    currentDestination = RouteDestinations.PROFILE
                    val repo = DexRepository(db)
                    val pvm: ProfileViewModel =
                        viewModel(factory = ProfileViewModel.provideFactory(repo))
                    ProfileScreen(pvm)
                }
            }
        )
    }
}
