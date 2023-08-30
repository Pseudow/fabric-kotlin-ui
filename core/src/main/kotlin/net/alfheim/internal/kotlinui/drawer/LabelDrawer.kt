package net.alfheim.internal.kotlinui.drawer

import net.alfheim.internal.kotlinui.dsl.label.Border
import net.alfheim.internal.kotlinui.dsl.label.Label
import net.alfheim.internal.kotlinui.dsl.label.Padding
import net.alfheim.internal.kotlinui.geometry.GeometryUtils
import net.alfheim.internal.kotlinui.geometry.MSAAFrameBuffer
import net.minecraft.client.gui.DrawContext
import kotlin.math.min

class LabelDrawer: Drawer {
    override fun computeDimensions(label: Label) {
        val computeOffsetWidth = label.offsetWidth == 0u
        val computeOffsetHeight = label.offsetHeight == 0u

        if(!computeOffsetHeight && !computeOffsetWidth)
            return

        for(spacing in arrayOf(label.margin, label.border, label.padding)) {
            if(spacing == null)
                continue

            if(computeOffsetWidth)
                label.offsetWidth += spacing.right + spacing.left
            if(computeOffsetHeight)
                label.offsetHeight += spacing.top + spacing.bottom
        }
    }

    override fun draw(label: Label, drawContext: DrawContext) {
        val matrices = drawContext.matrices

        var widthRemaining = label.totalWidth()
        var heightRemaining = label.totalHeight()
        for(spacing in arrayOf(label.margin, label.border, label.padding)) {
            if(spacing == null)
                continue

            val borderRound = label.border?.borderRound
            val insideWidth = label.totalWidth() - (label.margin?.width() ?: 0u)
            val insideHeight = label.totalHeight() - (label.margin?.height() ?: 0u)

            // Draw Border
            if(spacing is Border) {
                MSAAFrameBuffer.use(borderRound?.samples?.toInt() ?: 8) {
                    GeometryUtils.renderRoundedQuad(
                        matrices,
                        spacing.borderColor,
                        0.0,
                        0.0,
                        widthRemaining.toDouble(),
                        heightRemaining.toDouble(),
                        (borderRound?.percentage ?: 0f) * min(insideWidth, insideHeight).toFloat(),
                        borderRound?.samples ?: 8f
                    )
                }
            }

            // Draw Background and scissor
            if(spacing is Padding) {
                if(label.backgroundColor != null) {
                    MSAAFrameBuffer.use(borderRound?.samples?.toInt() ?: 8) {
                        GeometryUtils.renderRoundedQuad(
                            matrices,
                            label.backgroundColor,
                            0.0,
                            0.0,
                            widthRemaining.toDouble(),
                            heightRemaining.toDouble(),
                            (borderRound?.percentage ?: 0f) * min(insideWidth, insideHeight).toFloat(),
                            borderRound?.samples ?: 8f
                        )
                    }
                }

                if(label.backgroundImage != null) {
                    GeometryUtils.renderTexture(
                        matrices,
                        label.backgroundImage,
                        0.0,
                        0.0,
                        widthRemaining.toDouble(),
                        heightRemaining.toDouble()
                    )
                }
            }

            matrices.translate(
                spacing.right.toDouble(),
                spacing.top.toDouble(),
                0.0
            )

            widthRemaining -= spacing.right + spacing.left
            heightRemaining -= spacing.top + spacing.bottom
        }
    }

    override fun applicable(label: Label) = label.visible
}