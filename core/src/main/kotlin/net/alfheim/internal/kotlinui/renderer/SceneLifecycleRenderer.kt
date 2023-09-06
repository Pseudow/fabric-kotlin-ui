package net.alfheim.internal.kotlinui.renderer

import net.alfheim.internal.kotlinui.dsl.scene.Scene
import net.alfheim.internal.kotlinui.kit.Kit
import net.alfheim.tool.internal.kotlinui.kit.DefaultKit
import net.minecraft.client.gui.DrawContext

interface SceneLifecycleRenderer {
    fun submit(scene: Scene, kit: Kit = DefaultKit): Boolean

    fun render(scenes: Collection<Scene>, drawContext: DrawContext)

    fun applicable(scene: Scene): Boolean
}