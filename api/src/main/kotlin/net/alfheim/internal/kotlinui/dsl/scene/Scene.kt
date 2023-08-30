package net.alfheim.internal.kotlinui.dsl.scene

import net.alfheim.internal.kotlinui.dsl.label.Container

abstract class Scene {
    /**
     * This value contains the different contains that will be drawn
     * **depending on their location**.
     */
    abstract val containers: List<Container>

    /**
     * Scene id which will be used if a **server instance** requires the scene id.
     */
    abstract var id: String

    /**
     * This parameter allows the player **screen to be locked** when
     * the scene is drawn, which means the player will be able
     * to interact with all the components. *(Buttons...)*
     */
    var lockPlayer: Boolean = false

    /**
     * This parameter allows hiding scenes in order to keep them **in memory**
     * in order to reuse them. *(Player main menu usually shouldn't be re-built
     * each time)*
     */
    var visible: Boolean = true

    /**
     * This parameter allows removing the scene.
     */
    var remove: Boolean = false

    fun remove() {
        this.remove = true
    }
}