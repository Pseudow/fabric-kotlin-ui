package net.alfheim.internal.kotlinui.dsl.label

import net.minecraft.util.Identifier
import java.awt.Color
import java.net.URI

/**
 * Texts are **strings** that can be rendered on the screen,
 * specifying parameters such as **font**, **color** and so on.
 *
 * @see Label
 */
sealed class StringLabel(open var string: String = ""): Label() {
    /**
     * Represents the string value of this text.
     */

    var bold: Boolean = false
    var italic: Boolean = false
    var font: Identifier? = null

    /**
     * This parameter changes the **Text color**.
     * @see backgroundColor
     */
    var color: Color = Color.BLACK
}

data class Text(override var string: String = ""): StringLabel() {
    override val growable: Boolean = false
}

/**
 * Links are texts that can redirect when **being clicked** on to an **URL**.
 *
 * @see java.net.URI
 * @see Text
 * @see Label
 */
data class Link(var redirectURI: URI? = null): StringLabel() {
    override val growable: Boolean = false
}