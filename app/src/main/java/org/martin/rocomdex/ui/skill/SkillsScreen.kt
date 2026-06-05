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
import kotlin.collections.listOf

@Composable
fun SkillsScreen(viewModel: SkillsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchAllSkills()
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val skills = viewModel.skillsList.collectAsStateWithLifecycle().value
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(skills, key = { skill -> skill.id }) { skill ->
                SkillsListItem(skill, {})
            }
        }
    }
}

@Composable
fun SkillsListItem(skill: Skill, onClick: (Int) -> Unit = {}) {
    Card(
        onClick = { onClick(skill.id) },
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "file:///android_asset/icon/skills/${skill.res}.webp",
                contentDescription = skill.res,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                skill.name,
                modifier = Modifier.width(80.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            SkillTypeItemRow(skill.damageType, skill.skillType)
            Spacer(modifier = Modifier.width(4.dp))
            SimpleTable(
                SimpleTableData(
                    listOf("Power", "Energy"),
                    listOf(skill.damage?.toString() ?: "-", skill.energy.toString())
                ),
                40.dp, 40.dp,
                dataFontSize = 18.sp
            )
            Text(
                skill.desc,
                maxLines = 3,
                fontSize = 14.sp,
                lineHeight = 15.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxSize().padding(start=4.dp).wrapContentHeight(Alignment.CenterVertically)
            )
        }
    }
}


@Preview
@Composable
fun PreviewRow() {
    Column() {
        SkillsListItem(
            Skill(0, "abc", "def very long very long very long very long very long very long very long very long very long very long", 1,2,3,4,5,"101070", null )
        )
        SkillsListItem(
            Skill(0, "abc", "造成物伤，敌方每有1层冻结，自己回复1能量。", 4,8,3,4,5,"102029", null )
        )
    }
}