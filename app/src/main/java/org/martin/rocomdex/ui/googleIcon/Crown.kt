package org.martin.rocomdex.ui.googleIcon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val crown: ImageVector
    get() {
        if (_crown != null) {
            return _crown!!
        }
        _crown =
            ImageVector.Builder(
                name = "crown",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(5f, 20f)
                        verticalLineTo(18f)
                        horizontalLineTo(19f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(5f)
                        close()
                        moveTo(5f, 16.5f)
                        lineTo(3.73f, 8.48f)
                        quadToRelative(-0.05f, 0f, -0.11f, 0.01f)
                        reflectiveQuadTo(3.5f, 8.5f)
                        quadTo(2.88f, 8.5f, 2.44f, 8.06f)
                        reflectiveQuadTo(2f, 7f)
                        reflectiveQuadTo(2.44f, 5.94f)
                        reflectiveQuadTo(3.5f, 5.5f)
                        reflectiveQuadTo(4.56f, 5.94f)
                        reflectiveQuadTo(5f, 7f)
                        quadTo(5f, 7.18f, 4.96f, 7.32f)
                        reflectiveQuadTo(4.88f, 7.6f)
                        lineTo(8f, 9f)
                        lineTo(11.13f, 4.72f)
                        quadTo(10.85f, 4.52f, 10.68f, 4.2f)
                        reflectiveQuadTo(10.5f, 3.5f)
                        quadToRelative(0f, -0.63f, 0.44f, -1.06f)
                        reflectiveQuadTo(12f, 2f)
                        reflectiveQuadToRelative(1.06f, 0.44f)
                        reflectiveQuadTo(13.5f, 3.5f)
                        quadToRelative(0f, 0.38f, -0.17f, 0.7f)
                        reflectiveQuadTo(12.88f, 4.72f)
                        lineTo(16f, 9f)
                        lineTo(19.13f, 7.6f)
                        quadTo(19.08f, 7.47f, 19.04f, 7.32f)
                        reflectiveQuadTo(19f, 7f)
                        quadTo(19f, 6.38f, 19.44f, 5.94f)
                        reflectiveQuadTo(20.5f, 5.5f)
                        reflectiveQuadToRelative(1.06f, 0.44f)
                        reflectiveQuadTo(22f, 7f)
                        reflectiveQuadTo(21.56f, 8.06f)
                        reflectiveQuadTo(20.5f, 8.5f)
                        quadToRelative(-0.05f, 0f, -0.11f, -0.01f)
                        quadTo(20.33f, 8.48f, 20.28f, 8.48f)
                        lineTo(19f, 16.5f)
                        horizontalLineTo(5f)
                        close()
                        moveToRelative(1.7f, -2f)
                        horizontalLineTo(17.3f)
                        lineToRelative(0.65f, -4.18f)
                        lineToRelative(-2.62f, 1.15f)
                        lineTo(12f, 6.9f)
                        lineTo(8.68f, 11.48f)
                        lineTo(6.05f, 10.33f)
                        lineTo(6.7f, 14.5f)
                        close()
                        moveToRelative(5.3f, 0f)
                        close()
                    }
                }
                .build()
        return _crown!!
    }

private var _crown: ImageVector? = null
