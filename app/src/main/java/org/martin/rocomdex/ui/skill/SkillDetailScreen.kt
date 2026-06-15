package org.martin.rocomdex.ui.skill

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SkillDetailScreen(viewModel: SkillDetailViewModel, skid: Int) {
    LaunchedEffect(Unit) {
        viewModel.fetchSkill(skid)
    }
    //val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    //val skill = viewModel.skill.collectAsStateWithLifecycle().value
    Text("Here is skill ${skid}")
}