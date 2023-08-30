package net.alfheim.internal.kotlinui.dsl.scene

import net.alfheim.internal.kotlinui.dsl.label.Container
import net.alfheim.internal.kotlinui.dsl.label.Flex
import net.alfheim.internal.kotlinui.reactive.Reactive
import net.alfheim.internal.kotlinui.reactive.Storage
import java.util.*

/**
 * A screen is a scene that can take all space of the screen and
 * use different containers in different spaces.
 * It is used for complex menus or other render systems and so on.
 */
class Screen(override var id: String): Scene(), Reactive<Container, Position> {
    override val containers: List<Container> get() =
        this.storages.map { it.garbage }.flatten().map { it.first }.toList()

    override val storages: MutableList<Storage<Container, Position>> = LinkedList()
    override var reactiveQueue: MutableList<Pair<Container, Position>>? = null

    fun row(xPos: UInt, yPos: UInt, initializer: Container.() -> Unit) =
        this.container(Flex.ROW, xPos, yPos, initializer)

    fun container(xPos: UInt, yPos: UInt, initializer: Container.() -> Unit) =
        this.container(Flex.LINE, xPos, yPos, initializer)

    private fun container(flex: Flex, xPos: UInt, yPos: UInt, initializer: Container.() -> Unit) {
        val container = Container(flex)
        container.apply(initializer)

        this.addComponent(container, Position(xPos, yPos))
    }

    fun hide() {
        this.visible = false
    }

    fun show() {
        this.visible = true
    }
}

data class Position(val xPos: UInt, val yPos: UInt)

fun screen(id: String, initializer: Screen.() -> Unit): Screen {
    return Screen(id).apply(initializer)
}