package org.martin.rocomdex.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Immutable
data class SimpleTableData(val headers:List<String>, val values: List<String>)

@Composable
fun SimpleTable(data: SimpleTableData, normalWidth: Dp = 28.dp, lastWidth: Dp = 26.dp,
                headerFontSize: TextUnit = 12.sp, dataFontSize: TextUnit = 13.sp) {
    data.headers.indices.forEach { idx ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(if (idx == data.headers.size) lastWidth else normalWidth)
        ) {

            Text(
                text = data.headers[idx],
                fontSize = headerFontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = data.values[idx],
                fontSize = dataFontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}