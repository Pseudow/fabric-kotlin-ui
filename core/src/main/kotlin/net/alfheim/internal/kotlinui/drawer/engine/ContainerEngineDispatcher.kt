package net.alfheim.internal.kotlinui.drawer.engine

import net.alfheim.internal.kotlinui.drawer.*
import net.alfheim.internal.kotlinui.dsl.label.Container
import net.alfheim.internal.kotlinui.dsl.label.Flex
import net.alfheim.internal.kotlinui.dsl.label.Label
import net.alfheim.internal.kotlinui.event.EventTypes
import net.alfheim.internal.kotlinui.geometry.GeometryUtils
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.util.math.MatrixStack
import kotlin.math.max

class ContainerEngineDispatcher {
    private val workers = linkedSetOf(
        LabelDrawer(),
        SpaceDrawer(),
        TextDrawer(),
        IconDrawer(),
        ImageDrawer()
    )

    fun drawContainer(container: Container, drawContext: DrawContext) {
        this.computeContainerDimensions(container)

        val matrices = drawContext.matrices
        matrices.push()

        val scissor = this.internalDrawLabel(drawContext, container, false)

        container.getComponents().forEach { label ->
            matrices.push()

            if(label is Container)
                this.drawContainer(label, drawContext)
            else {
                this.internalDrawLabel(drawContext, label)
            }

            matrices.pop()

            when(container.flex) {
                Flex.LINE ->  matrices.translate(0F, label.totalHeight().toFloat(), 0F)
                Flex.ROW -> matrices.translate(label.totalWidth().toFloat(), 0F, 0F)
            }
        }

        if(scissor)
            GeometryUtils.endScissor()

        matrices.pop()
    }

    private fun internalDrawLabel(drawContext: DrawContext, label: Label, endScissor: Boolean = true): Boolean {
        val scissor = label.maxHeight != null || label.maxWidth != null
        this.computeLabelPosition(drawContext.matrices, label)

        if(label !is Container)
            drawContext.matrices.push()

        label.listeners[EventTypes.BEFORE_DRAW]?.forEach { it.accept(drawContext) }
        if(scissor)
            GeometryUtils.beginScissor(
                label.xPos.toDouble(),
                label.yPos.toDouble(),
                label.xPos + (label.maxWidth ?: label.totalWidth()).toDouble(),
                label.yPos + (label.maxHeight ?: label.totalHeight()).toDouble()
            )


        this.applicableWorkers(label).forEach {
            it.draw(label, drawContext)
        }

        if(scissor && endScissor)
            GeometryUtils.endScissor()
        label.listeners[EventTypes.AFTER_DRAW]?.forEach { it.accept(drawContext) }

        if(label !is Container)
            drawContext.matrices.pop()

        return scissor
    }

    private fun computeLabelPosition(matrices: MatrixStack, label: Label) {
        val positionMatrix = matrices.peek().positionMatrix

        label.xPos = positionMatrix.m30()
        label.yPos = positionMatrix.m31()
    }

    private fun computeContainerDimensions(container: Container) {
        val flex = container.flex
        var computedHeight = 0u
        var computedWidth = 0u

        this.applicableWorkers(container).forEach { it.computeDimensions(container) }

        container.getComponents().forEach { label ->
            if (label is Container) {
                this.computeLabelDimensions(label, container)
                this.computeContainerDimensions(label)
            } else if (label.height == null || label.width == null) {
                this.computeLabelDimensions(label, container)
            }

            val labelHeight: UInt = if(label.maxHeight != null)
                label.maxHeight!!
            else label.totalHeight()

            val labelWidth: UInt = if(label.maxWidth != null)
                label.maxWidth!!
            else label.totalWidth()

            when(flex) {
                Flex.LINE -> {
                    computedWidth = max(labelWidth, computedWidth)
                    computedHeight += labelHeight
                }
                Flex.ROW -> {
                    computedHeight = max(labelHeight, computedHeight)
                    computedWidth += labelWidth
                }
            }
        }

        container.width = max(container.width ?: 0u, computedWidth)
        container.height = max(container.height ?: 0u, computedHeight)
    }

    private fun computeLabelDimensions(label: Label, container: Container) {
        val flex = container.flex
        val specifiedHeight = container.height != null
        val specifiedWidth = container.width != null

        if(label.growable) {
            if (specifiedWidth && flex == Flex.LINE && container.width != null)
                label.width = max(label.width ?: 0u, container.width!!)

            if (specifiedHeight && flex == Flex.ROW && container.height != null)
                label.height = max(label.height ?: 0u, container.height!!)
        }

        this.applicableWorkers(label).forEach { it.computeDimensions(label) }
    }

    private fun applicableWorkers(label: Label) = this.workers.filter { it.applicable(label) }
}