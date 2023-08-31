package net.alfheim.internal.kotlinui.dsl.label

import java.awt.Color

/**
 * This is a special spacing which allows to add an **outside space after the border**.
 *
 * @see InternalSpacing
 * @see Border
 */
class Margin: InternalSpacing()

/**
 * This is a special spacing which allows to add an inside space **between the label and the border**.
 *
 * @see InternalSpacing
 * @see Border
 */
class Padding: InternalSpacing()

/**
 * This is a special spacing which allows to add a **border around a label** with special **properties** like *color*.
 *
 * @see InternalSpacing
 */
class Border: InternalSpacing() {
    var borderColor: Color = Color.BLACK
    /**
     * This parameter allows border to be rounded and not simple squares.
     */
    var borderRound: BorderRound? = null
}

data class BorderRound(val radius: Float, val samples: Float) {
    companion object {
        fun custom(radius: Float, samples: Float) = BorderRound(radius, samples)

        fun small() = BorderRound(8f, 8f)

        fun medium() = BorderRound(12f, 8f)

        fun large() = BorderRound(20f, 8f)

        fun full() = BorderRound(30f, 8f)
    }
}

/**
 * This class represents a **multi-direction** spacing that allows to **grow dimensions** of a label depending on the usage.
 *
 * @see Border
 * @see Margin
 * @see Border
 */
sealed class InternalSpacing {
    var top: UShort = 0u
    var bottom: UShort = 0u
    var left: UShort = 0u
    var right: UShort = 0u

    fun width() = this.left + this.right

    fun height() = this.top + this.bottom
}