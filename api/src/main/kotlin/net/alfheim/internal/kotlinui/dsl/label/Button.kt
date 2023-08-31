package net.alfheim.internal.kotlinui.dsl.label

import net.alfheim.internal.kotlinui.event.EventTypes
import java.awt.Color

/**
 * This interface allows grouping all **types of buttons with one interface**.
 */
interface Button {
    /**
     * This is the **main ability** of buttons: **being clicked**.
     */
    var onClick: Runnable?
    /**
     * This is the second **main ability** of buttons: **being released after click**.
     */
    var onReleased: Runnable?
}

/**
 * Text Buttons are simple **texts** that can be focused and clicked.
 * This class should be used with a **UI Kit**.
 *
 * @see Text
 */
data class TextButton(override var string: String = "", override var onClick: Runnable? = null) : StringLabel(), Button {
    override var growable: Boolean = false
    override var onReleased: Runnable? = null

    /**
     * In contrast with the background color, this value allows the button to change
     * **its background color** when the player focuses it.
     */
    var focusedBackgroundColor: Color? = null

    /**
     * In contrast with the border color, this value **allows the button to change
     * its border color** when the player focuses it.
     */
    var focusedBorderColor: Color? = null

    /**
     * This value is used by this class itself to perform background color changes.
     * **It should not be used in another context**.
     */
    private var backgroundColorCache: Color? = null
    /**
     * This value is used by this class itself to perform border color changes.
     * **It should not be used in another context**.
     */
    private var borderColorCache: Color? = null

    init {
        this.bind(EventTypes.FOCUSED) {
            this.backgroundColorCache = this.backgroundColor
            this.backgroundColor = this.focusedBackgroundColor

            this.borderColorCache = this.border?.borderColor
            this.focusedBorderColor?.let {
                this.border?.borderColor = it
            }
        }

        this.bind(EventTypes.UNFOCUSED) {
            this.backgroundColor = this.backgroundColorCache
            this.borderColorCache?.let {
                this.border?.borderColor = it
            }
        }
    }
}