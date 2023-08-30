package net.alfheim.internal.kotlinui.reactive

import java.util.*
import java.util.function.Consumer

typealias Subscriber<T> = Consumer<T>

/**
 * This class allows creating watchable data.
 */
class Observable<T>(private var value: T? = null) {
    private val subscribers = LinkedList<Subscriber<T>>()

    fun subscribe(subscriber: Subscriber<T>) {
        this.subscribers.add(subscriber)
    }

    fun set(newValue: T) {
        this.value = newValue

        this.broadcastChanges()
    }

    fun get(): T? = this.value

    fun apply(initializer: T.() -> Unit) {
        this.value?.apply(initializer)

        this.broadcastChanges()
    }

    fun broadcastChanges() {
        if(this.value == null)
            return

        this.subscribers.forEach { it.accept(this.value!!) }
    }

    companion object {
        fun <T>of(value: T) = Observable(value)

        fun <T>empty() = Observable<T>()
    }
}