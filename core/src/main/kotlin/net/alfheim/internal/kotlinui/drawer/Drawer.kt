package net.alfheim.internal.kotlinui.drawer

import net.alfheim.internal.kotlinui.dsl.label.Label
import net.minecraft.client.gui.DrawContext

interface Drawer {
    fun computeDimensions(label: Label)

    fun draw(label: Label, drawContext: DrawContext)

    fun applicable(label: Label): Boolean
}