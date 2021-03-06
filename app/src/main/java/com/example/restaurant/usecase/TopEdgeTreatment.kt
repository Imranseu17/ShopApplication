package com.example.restaurant.usecase

import com.google.android.material.shape.EdgeTreatment
import com.example.restaurant.usecase.TopEdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * This is a Top edge treatment for the bottom navigation bar, (customized from BottomAppBarTopEdgeTreatment.java)
 * which "cradles" a circular [FloatingActionButton].
 *
 *
 * This edge features a downward semi-circular cutout from the edge line. The two corners created
 * by the cutout can optionally be rounded. The circular cutout can also support a vertically offset
 * FloatingActionButton; i.e., the cut-out need not be a perfect semi-circle, but could be an arc= of
 * less than 180 degrees that does not start or finish with a vertical path. This vertical offset
 * must be positive.
 */
class TopEdgeTreatment(
    private val menuSize: Int,
    private val fabMenuIndex: Int,
    private val fabMargin: Float,
    private val roundedCornerRadius: Float,
    private val cradleVerticalOffset: Float
) : EdgeTreatment() {
    private var fabDiameter: Float? = null
    private val horizontalOffset: Float
    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        super.getEdgePath(length, center, interpolation, shapePath)
        if (fabDiameter == 0f) {
            // There is no cutout to draw.
            shapePath.lineTo(length, 0f)
            return
        }
        val cradleDiameter = fabMargin * 2.0f + fabDiameter!!
        val cradleRadius = cradleDiameter / 2.0f
        val roundedCornerOffset = interpolation * roundedCornerRadius
        val menuItemWidth = length / menuSize
        val fabPositionX = fabMenuIndex * menuItemWidth + menuItemWidth / 2
        //Float middle = center + this.horizontalOffset;
        val verticalOffset =
            interpolation * cradleVerticalOffset + (1.0f - interpolation) * cradleRadius
        val verticalOffsetRatio = verticalOffset / cradleRadius
        if (verticalOffsetRatio >= 1.0f) {
            // Vertical offset is so high that there's no curve to draw in the edge, i.e., the fab is
            // actually above the edge so just draw a straight line.
            shapePath.lineTo(length, 0.0f)
            return  // Early exit.
        }

        // Calculate the path of the cutout by calculating the location of two adjacent circles. One
        // circle is for the rounded corner. If the rounded corner circle radius is 0 the corner will
        // not be rounded. The other circle is the cutout.

        // Calculate the X distance between the center of the two adjacent circles using pythagorean
        // theorem.
        val distanceBetweenCenters = cradleRadius + roundedCornerOffset
        val distanceBetweenCentersSquared = distanceBetweenCenters * distanceBetweenCenters
        val distanceY = verticalOffset + roundedCornerOffset
        val distanceX =
            Math.sqrt((distanceBetweenCentersSquared - distanceY * distanceY).toDouble())
                .toFloat()

        // Calculate the x position of the rounded corner circles.
        val leftRoundedCornerCircleX = fabPositionX - distanceX
        val rightRoundedCornerCircleX = fabPositionX + distanceX

        // Calculate the arc between the center of the two circles.
        val cornerRadiusArcLength = Math.toDegrees(Math.atan((distanceX / distanceY).toDouble()))
            .toFloat()
        val cutoutArcOffset = 90.0f - cornerRadiusArcLength

        // Draw the starting line up to the left rounded corner.
        shapePath.lineTo(leftRoundedCornerCircleX - roundedCornerOffset, 0.0f)

        // Draw the arc for the left rounded corner circle. The bounding box is the area around the
        // circle's center which is at `(leftRoundedCornerCircleX, roundedCornerOffset)`.
        shapePath.addArc(
            leftRoundedCornerCircleX - roundedCornerOffset,
            0.0f,
            leftRoundedCornerCircleX + roundedCornerOffset,
            roundedCornerOffset * 2.0f,
            270.0f,
            cornerRadiusArcLength
        )

        // Draw the cutout circle.
        shapePath.addArc(
            fabPositionX - cradleRadius,
            -cradleRadius - verticalOffset,
            fabPositionX + cradleRadius,
            cradleRadius - verticalOffset,
            180.0f - cutoutArcOffset,
            cutoutArcOffset * 2.0f - 180.0f
        )

        // Draw an arc for the right rounded corner circle. The bounding box is the area around the
        // circle's center which is at `(rightRoundedCornerCircleX, roundedCornerOffset)`.
        shapePath.addArc(
            rightRoundedCornerCircleX - roundedCornerOffset,
            0.0f,
            rightRoundedCornerCircleX + roundedCornerOffset,
            roundedCornerOffset * 2.0f,
            270.0f - cornerRadiusArcLength,
            cornerRadiusArcLength
        )

        // Draw the ending line after the right rounded corner.
        shapePath.lineTo(length, 0.0f)
    }

    /**
     * Returns current fab diameter in pixels.
     */
    fun getFabDiameter(): Float {
        return fabDiameter!!
    }

    /**
     * Sets the fab diameter the size of the fab in pixels.
     */
    fun setFabDiameter(fabDiameter: Float?) {
        this.fabDiameter = fabDiameter
    }

    companion object {
        private val TAG = TopEdgeTreatment::class.java.simpleName
    }

    init {
        require(cradleVerticalOffset >= 0.0f) { "cradleVerticalOffset must be positive." }
        horizontalOffset = 0.0f
    }
}