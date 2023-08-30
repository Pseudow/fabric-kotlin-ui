package net.alfheim.internal.kotlinui.provider

import net.alfheim.internal.kotlinui.dsl.scene.Scene

interface SceneProvider {
    fun buildScene(): Scene
}