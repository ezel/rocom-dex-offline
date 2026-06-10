package org.martin.rocomdex.ui.skill


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.martin.rocomdex.data.Skill
import org.martin.rocomdex.ui.component.SimpleTable
import org.martin.rocomdex.ui.component.SimpleTableData
import org.martin.rocomdex.ui.component.SkillTypeItemRow
import org.martin.rocomdex.ui.component.SkillsListItem
import kotlin.collections.listOf

@Composable
fun SkillsScreen(viewModel: SkillsViewModel, onClick: (Int) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.fetchAllSkills()
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val skills = viewModel.skillsList.collectAsStateWithLifecycle().value
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(skills, key = { skill -> skill.id }) { skill ->
                SkillsListItem(skill, onClick)
            }
        }
    }
}