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
open class Text: Label() {
    override var growable: Boolean = true

    /**
     * Represents the string value of this text.
     */
    var string: String = ""

    var bold: Boolean = false
    var italic: Boolean = false
    var font: Identifier? = null

    /**
     * This parameter changes the **Text color**.
     * @see backgroundColor
     */
    var color: Color = Color.BLACK
}

/**
 * Links are texts that can redirect when **being clicked** on to an **URL**.
 *
 * @see java.net.URI
 * @see Text
 * @see Label
 */
class Link: Text() {
    var redirectURI: URI? = null
}