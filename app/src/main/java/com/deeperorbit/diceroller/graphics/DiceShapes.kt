package com.deeperorbit.diceroller.graphics

import android.graphics.Matrix
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.circle
import androidx.graphics.shapes.pill
import androidx.graphics.shapes.rectangle
import androidx.graphics.shapes.toPath
import androidx.graphics.shapes.transformed

/**
 * Material 3 Expressive Shapes for each dice number:
 * 1 -> Circle (RoundedPolygon.circle)
 * 2 -> Slanted Pill (RoundedPolygon.pill rotated at -42°)
 * 3 -> Soft Triangle (RoundedPolygon with 3 vertices and expressive corner smoothing)
 * 4 -> Slanted Rounded Rect (RoundedPolygon.rectangle with rounded corners and subtle slant)
 * 5 -> Soft Pentagon (RoundedPolygon with 5 vertices and expressive corner smoothing)
 * 6 -> Soft Hexagon (RoundedPolygon with 6 vertices and expressive corner smoothing)
 */
object Material3DiceShapes {

    private val polygonCache = mutableMapOf<Int, RoundedPolygon>()

    fun getTransformedPolygon(number: Int): RoundedPolygon {
        return polygonCache.getOrPut(number) {
            val basePolygon = when (number) {
                1 -> RoundedPolygon.circle(
                    numVertices = 16
                )
                2 -> RoundedPolygon.pill(
                    width = 1.9f,
                    height = 1.05f,
                    smoothing = 0.5f
                )
                3 -> RoundedPolygon(
                    numVertices = 3,
                    rounding = CornerRounding(radius = 0.38f, smoothing = 0.6f)
                )
                4 -> RoundedPolygon.rectangle(
                    width = 1.58f,
                    height = 1.72f,
                    rounding = CornerRounding(radius = 0.32f, smoothing = 0.6f)
                )
                5 -> RoundedPolygon(
                    numVertices = 5,
                    rounding = CornerRounding(radius = 0.36f, smoothing = 0.55f)
                )
                6 -> RoundedPolygon(
                    numVertices = 6,
                    rounding = CornerRounding(radius = 0.34f, smoothing = 0.6f)
                )
                else -> RoundedPolygon.circle(numVertices = 16)
            }

            val matrix = Matrix()
            when (number) {
                2 -> matrix.postRotate(-42f)
                3 -> matrix.postRotate(-90f)
                4 -> matrix.postRotate(-6.5f)
                5 -> matrix.postRotate(-90f)
                6 -> matrix.postRotate(0f)
            }

            basePolygon.transformed(matrix)
        }
    }
}

/**
 * Draws the Material 3 Morph transition between two shapes.
 */
fun DrawScope.drawMorphDiceShape(
    startNumber: Int,
    endNumber: Int,
    progress: Float,
    color: Color = Color(0xFF111111)
) {
    val startPolygon = Material3DiceShapes.getTransformedPolygon(startNumber)
    val endPolygon = Material3DiceShapes.getTransformedPolygon(endNumber)
    val morph = Morph(startPolygon, endPolygon)

    val androidPath = morph.toPath(progress = progress.coerceIn(0f, 1f))
    val bounds = android.graphics.RectF()
    androidPath.computeBounds(bounds, true)

    val currentWidth = if (bounds.width() > 0) bounds.width() else 1f
    val currentHeight = if (bounds.height() > 0) bounds.height() else 1f

    val targetSize = minOf(size.width, size.height) * 0.88f
    val scale = minOf(targetSize / currentWidth, targetSize / currentHeight)

    val matrix = Matrix()
    matrix.postTranslate(-bounds.centerX(), -bounds.centerY())
    matrix.postScale(scale, scale)
    matrix.postTranslate(size.width / 2f, size.height / 2f)

    androidPath.transform(matrix)
    val composePath = androidPath.asComposePath()

    drawPath(path = composePath, color = color, style = Fill)
}

/**
 * Draws a static Material 3 Expressive shape for the given number.
 */
fun DrawScope.drawDiceShape(
    number: Int,
    color: Color = Color(0xFF111111)
) {
    drawMorphDiceShape(
        startNumber = number,
        endNumber = number,
        progress = 0f,
        color = color
    )
}
