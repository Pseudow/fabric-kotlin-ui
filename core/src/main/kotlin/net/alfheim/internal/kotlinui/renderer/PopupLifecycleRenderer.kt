package net.alfheim.internal.kotlinui.renderer

import net.alfheim.internal.kotlinui.dsl.label.Container
import net.alfheim.internal.kotlinui.dsl.label.Flex
import net.alfheim.internal.kotlinui.dsl.scene.Popup
import net.alfheim.internal.kotlinui.dsl.scene.Scene
import net.alfheim.internal.kotlinui.kit.Kit
import net.alfheim.internal.kotlinui.ContainerEngineDispatcher
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.util.math.MatrixStack
import java.util.concurrent.atomic.AtomicInteger

class PopupLifecycleRenderer(private val dispatcher: ContainerEngineDispatcher): SceneLifecycleRenderer {
    private val popupDelays = HashMap<String, AtomicInteger>()
    private val popupTimeout: Int = 200
    private val popupGap: Int = 10
    private val popupMaxWidth: UInt = 200u
    private val popupMaxHeight: UInt = 50u

    override fun submit(scene: Scene, kit: Kit): Boolean {
        val popup = scene as Popup
        val container = Container(Flex.LINE).apply(kit.app {
            width = popupMaxWidth
            maxWidth = popupMaxWidth
            height = popupMaxHeight
            maxHeight = popupMaxHeight

            padding(3u)

            row {
                popup.icon?.let {
                    addComponent(it, Unit)

                    space(5u)
                }

                popup.title?.let { addComponent(it, Unit) }
            }

            space(5u)
            popup.containers.forEach { addComponent(it, Unit) }
        })

        popup.containers[0] = container

        this.popupDelays[popup.id] = AtomicInteger()

        return true
    }

    override fun render(scenes: Collection<Scene>, drawContext: DrawContext) {
        val matrices = drawContext.matrices
        val startCoords = this.getStartCoords()

        matrices.push()
        matrices.translate(startCoords.first, startCoords.second, 0.0)

        scenes.reversed().take(5).forEach { scene ->
            if(this.popupDelays[scene.id]!!.get() >= this.popupTimeout) {
                this.popupDelays.remove(scene.id)
                scene.remove()

                return@forEach
            }

            val animate = this.fadeInAnimation(this.popupDelays[scene.id]!!.incrementAndGet(), matrices)
            this.dispatcher.drawContainer(
                scene.containers.first(),
                drawContext
            )

            if(animate)
                matrices.pop()

            matrices.translate(0f, -this.popupMaxHeight.toFloat() - this.popupGap, 0f)
        }

        matrices.pop()
    }

    private fun fadeInAnimation(value: Int, matrixStack: MatrixStack): Boolean {
        val fromXGap = 20.0
        val animationTick = 50

        if(value > animationTick)
            return false

        val xGap = value * fromXGap / animationTick

        matrixStack.push()
        matrixStack.translate(fromXGap - xGap, 0.0, 0.0)

        return true
    }

    private fun getStartCoords(): Pair<Double, Double> {
        val client = MinecraftClient.getInstance()

        val x = client.window.scaledWidth * 0.97 - this.popupMaxWidth.toInt()
        val y = client.window.scaledHeight * 0.97 - this.popupMaxHeight.toInt()

        return Pair(x, y)
    }

    override fun applicable(scene: Scene) = scene is Popup
}