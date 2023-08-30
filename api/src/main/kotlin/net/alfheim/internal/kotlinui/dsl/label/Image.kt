package net.alfheim.internal.kotlinui.dsl.label

import net.minecraft.util.Identifier

class Image(val location: Identifier, width: UInt, height: UInt): Label() {
    override val growable: Boolean = false

    init {
        this.height = height
        this.width = width
    }
}