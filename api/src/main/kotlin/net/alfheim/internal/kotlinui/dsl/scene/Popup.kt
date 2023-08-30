package net.alfheim.internal.kotlinui.dsl.scene

import net.alfheim.api.kotlinui.dsl.label.*
import net.alfheim.internal.kotlinui.dsl.label.*
import net.minecraft.util.Identifier
import java.util.*

/**
 * A popup is a small container which purpose is to notify the player
 * for non-essential things such as events, broadcasts and so on.
 */
class Popup(override var id: String): Scene() {
    override val containers: MutableList<Container> = LinkedList()

    var title: Text? = null
    var icon: Icon? = null

    fun title(string: String, initializer: Text.() -> Unit): Text {
        val text = Text()
        text.string = string
        text.bold = true
        text.apply(initializer)

        this.title = text

        return text
    }

    fun icon(resource: Identifier, size: IconSize) {
        this.icon = Icon(resource, size)
    }

    fun container(initializer: Container.() -> Unit) {
        val container = Container(Flex.LINE).apply(initializer)

        if(this.containers.isNotEmpty())
            this.containers[0] = container
        else this.containers.add(container)
    }
}

fun popup(id: String, initializer: Popup.() -> Unit): Popup {
    return Popup(id).apply(initializer)
}