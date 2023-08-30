package net.alfheim.internal.kotlinui.drawer

import net.alfheim.internal.kotlinui.dsl.label.Image
import net.alfheim.internal.kotlinui.dsl.label.Label
import net.alfheim.internal.kotlinui.geometry.GeometryUtils
import net.minecraft.client.gui.DrawContext

class ImageDrawer: Drawer {
    override fun computeDimensions(label: Label) {
        if(label.height == null)
            label.height = 100u
        if(label.width == null)
            label.width = 100u
    }

    override fun draw(label: Label, drawContext: DrawContext) {
        val icon = label as Image

        GeometryUtils.renderTexture(
            drawContext.matrices,
            icon.location,
            0.0,
            0.0,
            icon.width!!.toDouble(),
            icon.height!!.toDouble()
        )
    }

    override fun applicable(label: Label) = label.visible && label is Image
}