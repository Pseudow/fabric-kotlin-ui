package net.alfheim.internal.kotlinui.drawer

import net.alfheim.internal.kotlinui.dsl.label.Label
import net.alfheim.internal.kotlinui.dsl.label.Text
import net.alfheim.internal.kotlinui.extensions.times
import net.alfheim.internal.kotlinui.extensions.wrapLinesWithStyle
import net.alfheim.internal.kotlinui.geometry.GeometryUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text.literal
import kotlin.math.max

class TextDrawer: Drawer {
    private var client = MinecraftClient.getInstance()
    private val fontHeight = 9u

    override fun computeDimensions(label: Label) {
        val textRenderer = this.client.textRenderer
        val text = label as Text

        val computedHeight: UInt
        if(label.width != null) {
            val wrappedLines = textRenderer.wrapLinesWithStyle(this.transform(text), label.width!! - label.offsetHeight)
            computedHeight = wrappedLines.size * this.fontHeight
        } else {
            label.width = textRenderer.getWidth(this.transform(text)).toUInt()
            computedHeight = if(label.string.isNotEmpty()) this.fontHeight else 0u
        }

        label.height = max(label.height ?: 0u, computedHeight)
    }

    override fun draw(label: Label, drawContext: DrawContext) {
        val text = label as Text

        GeometryUtils.renderString(
            drawContext,
            text.color,
            0,
            0,
            text.totalWidth().toInt(),
            this.transform(text)
        )
    }

    override fun applicable(label: Label) = label.visible && label is Text

    private fun transform(text: Text) = literal(text.string).styled {
        it.withFont(text.font)
            .withBold(text.bold)
            .withItalic(text.italic)
    }
}