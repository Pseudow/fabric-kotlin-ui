package net.alfheim.internal.kotlinui.dsl.label

import net.minecraft.util.Identifier


/**
 * Icons are **images** that are **size-defined** and can actually be re-used anywhere
 * as their size can not be modified.
 *
 * @see IconSize
 * @see Label
 */
data class Icon(var resource: Identifier, var size: IconSize): Label() {
    override var growable: Boolean = false
}


/**
 * IconSize defines all the **sizes** an icon can use.
 *
 * @see Icon
 */
enum class IconSize(val height: UInt, val width: UInt) {
    BIG(32u, 32u),
    MEDIUM(16u, 16u),
    SMALL(8u, 8u)
}