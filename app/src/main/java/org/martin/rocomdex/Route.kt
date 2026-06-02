package org.martin.rocomdex

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
data object RouteSearch : NavKey

@Serializable
data class RoutePets(val id: String) : NavKey

@Serializable
data class RouteSkills(val id: String) : NavKey

@Serializable
data class RouteTags(val id: String) : NavKey

@Serializable
data class RouteProfile(val id: String) : NavKey


enum class RouteDestinations(
    val label: String,
    val icon: Int,
    val route: NavKey
) {
    HOME("查找", R.drawable.ic_home, RouteSearch),
    PETS("精灵", R.drawable.ic_home, RoutePets("")),
    SKILLS("技能", R.drawable.ic_home, RouteSkills("")),
    FAVOURITE("收藏", R.drawable.ic_favorite, RouteTags("")),
    PROFILE("关于", R.drawable.ic_account_box, RouteProfile("")),
}