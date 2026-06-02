package org.martin.rocomdex

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.martin.rocomdex.data.DexDatabase
import org.martin.rocomdex.data.DexRepository
import org.martin.rocomdex.ui.HomeScreen
import org.martin.rocomdex.ui.pet.PetsScreen
import org.martin.rocomdex.ui.profile.ProfileScreen
import org.martin.rocomdex.ui.profile.ProfileViewModel
import org.martin.rocomdex.ui.skill.SkillsScreen
import org.martin.rocomdex.ui.theme.RocomDexTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Main","Create database from asset")
        val db = Room.databaseBuilder(
            applicationContext,
                DexDatabase::class.java, "data.db"
            ).createFromAsset("rocom.db").fallbackToDestructiveMigration(false).build()
        Log.d("Main","Created database from asset")

        enableEdgeToEdge()
        setContent {
            RocomDexTheme {
                RocomDexApp(db)
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
                    val repo = DexRepository(db)
                    val pvm: ProfileViewModel =
                        viewModel(factory = ProfileViewModel.provideFactory(repo))
                    ProfileScreen(pvm)
                }
            }
        )
    }
}
