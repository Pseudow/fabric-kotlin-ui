package net.alfheim.internal.kotlinui.renderer

import net.alfheim.internal.kotlinui.dsl.scene.Scene
import net.alfheim.internal.kotlinui.dsl.scene.Screen
import net.alfheim.internal.kotlinui.kit.Kit
import net.alfheim.internal.kotlinui.ContainerEngineDispatcher
import net.minecraft.client.gui.DrawContext

class ScreenLifecycleRenderer(private val dispatcher: ContainerEngineDispatcher): SceneLifecycleRenderer {
    override fun submit(scene: Scene, kit: Kit): Boolean {
        return true
    }

    override fun render(scenes: Collection<Scene>, drawContext: DrawContext) {
        scenes.forEach { scene ->
            val screen = scene as Screen
            val matrices = drawContext.matrices

            screen.storages.map { it.garbage }.flatten().forEach { storage ->
                matrices.push()
                matrices.translate(storage.second.xPos.toFloat(), storage.second.yPos.toFloat(), 0F)

                this.dispatcher.drawContainer(
                    storage.first,
                    drawContext
                )

                matrices.pop()
            }
        }
    }

    override fun applicable(scene: Scene) = scene is Screen
}