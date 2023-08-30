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

class BorderRound(val percentage: Float, val samples: Float) {
    companion object {
        fun custom(percentage: Float, samples: Float) = BorderRound(percentage, samples)

        fun small() = BorderRound(0.15f, 8f)

        fun medium() = BorderRound(0.20f, 8f)

        fun large() = BorderRound(0.30f, 8f)

        fun full() = BorderRound(0.80f, 8f)
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