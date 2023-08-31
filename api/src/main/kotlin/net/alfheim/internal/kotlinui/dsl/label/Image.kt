package net.alfheim.internal.kotlinui.dsl.label

import net.minecraft.util.Identifier

data class Image(val location: Identifier,
                 override var width: UInt?,
                 override var height: UInt?): Label() {
    override val growable: Boolean = false
}