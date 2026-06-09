package org.martin.rocomdex.ui.pet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.martin.rocomdex.data.PetDetailModel
import org.martin.rocomdex.ui.component.TypeItemRow
import org.martin.rocomdex.ui.skill.SkillsListItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailScreen(viewModel: PetsViewModel, petId: Int) {
    LaunchedEffect(Unit) {
        viewModel.fetchPet(petId)
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val pet = viewModel.pet.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    // TODO: use AnnotatedString
                    Row() {
                        Text(
                            text = buildAnnotatedString {
                                append(pet.pet.name)
                                val startForm = length
                                if (pet.pet.form != null) {
                                    append("-" + pet.pet.form)

                                    addStyle(
                                        style = SpanStyle(
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        start = startForm,
                                        end = length
                                    )
                                }

                                val startHid = length
                                append(" #" + pet.pet.hid)
                                addStyle(
                                    style = SpanStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight(100),
                                        color = MaterialTheme.colorScheme.onSurface
                                    ),
                                    start = startHid,
                                    end = length
                                )

                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        PetCard(pet, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun PetCard(pwf: PetDetailModel, modifier: Modifier = Modifier) {
    val pet = pwf.pet;
    val feature = pwf.feature;
    val skills = pwf.skills;

    Surface(modifier = modifier) {
        LazyColumn() {
            item {
                // basic information
                Row() {
                    AsyncImage(
                        model = "file:///android_asset/icon/pets/${pet.res}.webp",
                        contentDescription = pet.res,
                        modifier = Modifier.size(160.dp)
                    )
                    Column() {
                        Text("Types:")
                        TypeItemRow(pet.type1Id, pet.type2Id, Modifier.padding(start = 10.dp))
                        Text("Feature:")
                        Row() {
                            AsyncImage(
                                model = "file:///android_asset/icon/skills/${feature.res}.webp",
                                contentDescription = feature.res,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp)
                                    .border(BorderStroke(2.dp, Color.White), CircleShape)
                                    .clip(CircleShape)
                            )
                            Column() {
                                Text(feature.name)
                                Text(feature.desc)
                            }
                        }
                    }
                }
            }
            item {
                // stats
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                ) {
                    Text("Base stats:", modifier = Modifier.width(100.dp))
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                            .width(160.dp)
                    )

                    Text("min-", fontSize = 12.sp, modifier = Modifier.width(34.dp))
                    Text("min", fontSize = 12.sp, modifier = Modifier.width(34.dp))
                    Text("max", fontSize = 12.sp, modifier = Modifier.width(34.dp))
                    Text("max+", fontSize = 12.sp, modifier = Modifier.width(34.dp))
                }
                RaceRow("HP:", pwf.stats["hp"]!!)
                RaceRow("Atk:", pwf.stats["atk"]!!)
                RaceRow("SAtk:", pwf.stats["satk"]!!)
                RaceRow("Def:", pwf.stats["def"]!!)
                RaceRow("SDef:", pwf.stats["sdef"]!!)
                RaceRow("Spd:", pwf.stats["spd"]!!)
                Text("Evolution:")

                Text("Move Lists")
            }
            items(skills, key = { skill -> skill.skill.id }) { skill ->
                SkillsListItem(skill.skill, { })
            }
        }
    }
}

@Composable
fun RaceRow(title: String, value: List<Int>) {
    Row() {
        Text(
            "${title}${value[0]}",
            fontSize = 14.sp,
            modifier = Modifier.width(60.dp)
        )
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .width(200.dp)
                .drawBehind {
                    drawRect(
                        // shadow
                        color = Color.Red,
                        size = Size(value[0].dp.toPx(), 2.dp.toPx())
                    )
                    drawRect(
                        // context
                        color = Color.Blue,
                        topLeft = Offset(x = 0f, y = 2.dp.toPx()),
                        size = Size(value[0].dp.toPx(), 11.dp.toPx())
                    )
                }

        )
        Text(value[1].toString(), modifier = Modifier.width(34.dp))
        Text(value[2].toString(), modifier = Modifier.width(34.dp))
        Text(value[3].toString(), modifier = Modifier.width(34.dp))
        Text(value[4].toString(), modifier = Modifier.width(34.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPetDetail() {
    //PetCard(PetDetailModel())

    val petName = "asdasda"
    val petForm = "xxxxxa"
    val petHid = 456
    Text(
        text = buildAnnotatedString {
            append(petName)
            val startForm = length
            if (petForm != null) {
                append("-" + petForm)

                addStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    start = startForm,
                    end = length
                )
            }

            val startHid = length
            append(" #" + petHid)
            addStyle(
                style = SpanStyle(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                start = startHid,
                end = length
            )
        },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
