package org.martin.rocomdex.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.martin.rocomdex.R

private data class TypeInfo(
    val id: Int,
    val stringResId: Int,
    val colorResId: Int
)

private val TypeInfoMap = mapOf(
    2 to TypeInfo(2, R.string.type_2, R.color.type_2),
    3 to TypeInfo(3, R.string.type_3, R.color.type_3),
    4 to TypeInfo(4, R.string.type_4, R.color.type_4),
    5 to TypeInfo(5, R.string.type_5, R.color.type_5),
    6 to TypeInfo(6, R.string.type_6, R.color.type_6),
    8 to TypeInfo(8, R.string.type_8, R.color.type_8),
    9 to TypeInfo(9, R.string.type_9, R.color.type_9),
    10 to TypeInfo(10, R.string.type_10, R.color.type_10),
    11 to TypeInfo(11, R.string.type_11, R.color.type_11),
    12 to TypeInfo(12, R.string.type_12, R.color.type_12),
    13 to TypeInfo(13, R.string.type_13, R.color.type_13),
    14 to TypeInfo(14, R.string.type_14, R.color.type_14),
    15 to TypeInfo(15, R.string.type_15, R.color.type_15),
    16 to TypeInfo(16, R.string.type_16, R.color.type_16),
    17 to TypeInfo(17, R.string.type_17, R.color.type_17),
    18 to TypeInfo(18, R.string.type_18, R.color.type_18),
    19 to TypeInfo(19, R.string.type_19, R.color.type_19),
    20 to TypeInfo(20, R.string.type_20, R.color.type_20)
)

private val SkillTypeInfoMap = mapOf(
    1 to TypeInfo(1, R.string.skillType_1, R.color.skill_type_1),
    2 to TypeInfo(2, R.string.skillType_2, R.color.skill_type_2),
    3 to TypeInfo(3, R.string.skillType_3, R.color.skill_type_3),
    4 to TypeInfo(4, R.string.skillType_4, R.color.skill_type_4),
)

@Composable
fun TypeItem(type1Id: Int, type2Id: Int?) {
    Column(
        modifier = Modifier
            .height(44.dp)
            .width(32.dp)
        ,
        verticalArrangement = Arrangement.Center
    ) {
        TypeBar(type1Id)
        if (type2Id != null) TypeBar(type2Id)
    }
}

@Composable
fun TypeItemRow(type1Id: Int, type2Id: Int?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(22.dp)
            .width(64.dp)
    ) {
        TypeBar(type1Id)
        if (type2Id != null) {
            Spacer(modifier=Modifier.width(2.dp))
            TypeBar(type2Id)
        }
    }
}

@Composable
fun SkillTypeItem(typeId: Int, skillTypeId: Int) {
    Column(
        modifier = Modifier.height(46.dp).width(32.dp)
    ) {
        TypeBar(typeId)
        Spacer(modifier=Modifier.height(2.dp))
        SkillTypeBar(skillTypeId)
    }
}

@Composable
fun SkillTypeItemRow(typeId: Int, skillTypeId: Int) {
    Row(
        modifier = Modifier.height(22.dp).width(64.dp)
    ) {
        TypeBar(typeId)
        Spacer(modifier=Modifier.width(2.dp))
        SkillTypeBar(skillTypeId)
    }
}
@Composable
fun SkillTypeBar(skillType: Int) {
    val typeInfo = SkillTypeInfoMap[skillType]!!
    Text(
        stringResource(id = typeInfo.stringResId),
        color = colorResource(R.color.white),
        fontSize = 10.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(
                color = colorResource(id = typeInfo.colorResId),
                shape = RoundedCornerShape(2.dp)
            )
            .height(22.dp)
            .width(32.dp)
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun TypeBar(typeId: Int) {
    val typeInfo = TypeInfoMap[typeId]!!
    Text(
        stringResource(id = typeInfo.stringResId),
        color = colorResource(R.color.white),
        fontSize = 10.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(
                color = colorResource(id = typeInfo.colorResId),
                shape = RoundedCornerShape(2.dp)
            )
            .height(22.dp)
            .width(32.dp)
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Preview
@Composable
fun PreviewTypeBar() {
    //TypeItemRow(10, 2)
    SkillTypeItem(2, 1)
    //SkillTypeItemRow(3, 2)
}