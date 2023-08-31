package net.alfheim.internal.kotlinui.dsl.label

import net.alfheim.internal.kotlinui.reactive.Reactive
import net.alfheim.internal.kotlinui.reactive.Storage
import net.minecraft.util.Identifier
import java.net.URI
import java.util.*

/**
 * Containers allow to stack **labels** such as *texts*, *buttons* and so on.
 * This is based on the **HTML div** object.
 *
 * @see Label
 */
data class Container(val flex: Flex): Label(), Reactive<Label, Unit> {
    override var growable: Boolean = true

    override val storages: MutableList<Storage<Label, Unit>> = LinkedList()
    override var reactiveQueue: MutableList<Pair<Label, Unit>>? = null

    fun row(width: UInt, height: UInt, initializer: Container.() -> Unit) = this.container(Flex.ROW) {
        this.width = width
        this.height = height

        this.apply(initializer)
    }

    fun row(initializer: Container.() -> Unit) = this.container(Flex.ROW) {
        this.apply(initializer)
    }

    fun container(width: UInt, height: UInt, initializer: Container.() -> Unit) = this.container {
        this.width = width
        this.height = height

        this.apply(initializer)
    }

    fun container(initializer: Container.() -> Unit) = this.container(Flex.LINE, initializer)

    private fun container(flex: Flex, initializer: Container.() -> Unit): Container {
        val container = Container(flex).apply(initializer)
        this.addComponent(container, Unit)

        return container
    }

    fun text(string: String) = text {
        this.string = string
    }

    fun text(string: String, initializer: Text.() -> Unit) = text {
        this.string = string

        this.apply(initializer)
    }

    fun text(initializer: Text.() -> Unit): Text {
        val text = Text().apply(initializer)
        this.addComponent(text, Unit)

        return text
    }

    fun link(string: String, redirectURI: URI) = link {
        this.string = string
        this.redirectURI = redirectURI
    }

    fun link(string: String, redirectURI: URI, initializer: Link.() -> Unit) = link {
        this.string = string
        this.redirectURI = redirectURI

        this.apply(initializer)
    }

    fun link(initializer: Link.() -> Unit): Link {
        val link = Link().apply(initializer)
        this.addComponent(link, Unit)

        return link
    }

    fun textButton(string: String, onClick: Runnable, initializer: TextButton.() -> Unit) = textButton {
        this.string = string
        this.onClick = onClick

        this.apply(initializer)
    }

    fun textButton(initializer: TextButton.() -> Unit): TextButton {
        val textButton = TextButton().apply(initializer)
        this.addComponent(textButton, Unit)

        return textButton
    }

    fun image(location: Identifier, width: UInt, height: UInt) = image(location, width, height) {}

    fun image(location: Identifier, width: UInt, height: UInt, initializer: Image.() -> Unit): Image {
        val image = Image(location, width, height).apply(initializer)
        this.addComponent(image, Unit)

        return image
    }

    fun space(value: UInt) = space {
        this.value = value
    }

    fun space(value: UInt, initializer: Space.() -> Unit) = space {
        this.value = value

        this.apply(initializer)
    }

    fun space(initializer: Space.() -> Unit): Space {
        val space = Space(this.flex).apply(initializer)
        this.addComponent(space, Unit)

        return space
    }

    fun icon(resource: Identifier) = icon(resource, IconSize.SMALL) {}

    fun icon(resource: Identifier, size: IconSize) = icon(resource, size) {}

    fun icon(resource: Identifier, size: IconSize, initializer: Icon.() -> Unit): Icon {
        val icon = Icon(resource, size).apply(initializer)
        this.addComponent(icon, Unit)

        return icon
    }
}

/**
 * This class describes the **direction** a container can grow.
 * This is based on the **HTML flex** notion.
 */
enum class Flex {
    /**
     * This flex allows the container to grow **vertically**, which means child labels will be stacked on the **y-axis**.
     */
    LINE,

    /**
     * This flex allows the container to grow **horizontally**, which means child labels will be stacked on the **x-axis**.
     */
    ROW
}