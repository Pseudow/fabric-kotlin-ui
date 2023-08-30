package net.alfheim.internal.kotlinui.drawer

import net.alfheim.internal.kotlinui.dsl.label.Icon
import net.alfheim.internal.kotlinui.dsl.label.Label
import net.alfheim.internal.kotlinui.geometry.GeometryUtils
import net.minecraft.client.gui.DrawContext

class IconDrawer: Drawer {
    override fun computeDimensions(label: Label) {
        val icon = label as Icon

        icon.width = icon.size.width
        icon.height = icon.size.height
    }

    override fun draw(label: Label, drawContext: DrawContext) {
        val icon = label as Icon

        GeometryUtils.renderTexture(
            drawContext.matrices,
            icon.resource,
            0.0,
            0.0,
            icon.size.width.toDouble(),
            icon.size.height.toDouble()
        )
    }

    override fun applicable(label: Label) = label.visible && label is Icon
}