package net.alfheim.internal.kotlinui.drawer

import net.alfheim.internal.kotlinui.dsl.label.Flex
import net.alfheim.internal.kotlinui.dsl.label.Label
import net.alfheim.internal.kotlinui.dsl.label.Space
import net.minecraft.client.gui.DrawContext

class SpaceDrawer: Drawer {
    override fun computeDimensions(label: Label) {
        val space = label as Space

        if(space.flex == Flex.ROW)
            space.width = space.value
        else space.height = space.value
    }

    override fun draw(label: Label, drawContext: DrawContext) {
    }

    override fun applicable(label: Label) = label.visible && label is Space
}