package org.martin.rocomdex.ui.pet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.martin.rocomdex.data.Pet
import org.martin.rocomdex.ui.component.SimpleTable
import org.martin.rocomdex.ui.component.SimpleTableData
import org.martin.rocomdex.ui.component.TypeItem
import kotlin.collections.listOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetsScreen(viewModel: PetsViewModel, clickPetOnList: (Int) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.fetchAllPets()
    }
    // 定义缓存窗口：在滚动方向上提前预提取 150.dp，并在滑出视口后保留 100.dp 的项目
    //val dpCacheWindow = LazyLayoutCacheWindow(ahead = 150.dp, behind = 100.dp)

    // 也可以按比例设置：例如提前预取相当于列表长度 100% 的内容
    val fractionCacheWindow = LazyLayoutCacheWindow(aheadFraction = 2f, behindFraction = 0.5f)

    val state = rememberLazyListState(cacheWindow = fractionCacheWindow)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val pets = viewModel.petsList.collectAsStateWithLifecycle().value
        LazyColumn(state = state, modifier = Modifier.padding(innerPadding)) {
            items(pets, key = { pet -> pet.id }) { pet ->
                PetsListItem(pet, clickPetOnList)
            }
        }
    }
}

@Composable
fun PetsListItem(pet: Pet, onClick: (Int) -> Unit = {}) {
    Card(
        onClick = { onClick(pet.id) },
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                pet.hid.toString(),
                fontSize = 8.sp,
                modifier = Modifier.width(16.dp)
            )
            AsyncImage(
                model = "file:///android_asset/icon/icons/${pet.res}.webp",
                contentDescription = pet.res,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            if (pet.form != null) {
                Text(
                    text = buildAnnotatedString {
                        append(pet.name)
                        append("\n")
                        val startForm = length
                        append(pet.form)

                        addStyle(
                            style = SpanStyle(fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant ),
                            start = startForm,
                            end = length
                        )
                    },
                    modifier = Modifier.width(110.dp),
                    lineHeight = 1.2.em,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text(
                    pet.name,
                    modifier = Modifier.width(110.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            TypeItem(pet.type1Id, pet.type2Id)
            Spacer(modifier = Modifier.width(2.dp))
            SimpleTable(
                SimpleTableData(
                    listOf("HP", "Atk", "SpA", "Def", "SpD", "Spe", "BST"),
                    listOf(
                        pet.raceHP.toString(), pet.racePAtk.toString(), pet.raceSAtk.toString(),
                        pet.racePDef.toString(), pet.raceSDef.toString(), pet.raceSpe.toString(), pet.raceSum.toString()
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewRow() {
    Column() {
        PetsListItem(
            Pet(
                110, 110, "NoName", 0, 5, 2, 0, "thisform2", null,
                120, 320, 220, 110, 110, 330, 440, 30,null, null, null, "", null
            )
        )
        PetsListItem(
            Pet(
                110, 110, "NoName", 0, 5, 2, 0, null, null,
                120, 320, 220, 110, 110, 330, 440, 20,null, null, null, "", null
            )
        )
    }
}