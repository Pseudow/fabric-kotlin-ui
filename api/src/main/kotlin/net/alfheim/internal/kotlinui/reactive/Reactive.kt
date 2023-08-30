package net.alfheim.internal.kotlinui.reactive

import net.alfheim.internal.kotlinui.dsl.label.Label
import java.util.*

/**
 * This interface is implemented by the component that can be reactive.
 * It acts by adding an extra layer to components that allow reloading them exclusively.
 */
interface Reactive<T: Label, D> {
    val storages: MutableList<Storage<T, D>>
    var reactiveQueue: MutableList<Pair<T, D>>?

    fun <U>invoke(value: U, initializer: Reactive<T, D>.(U) -> Unit): Collection<Pair<T, D>> {
        this.reactiveQueue = LinkedList()
        initializer.invoke(this, value)

        val cache = ArrayList(this.reactiveQueue!!)

        this.reactiveQueue!!.clear()

        return cache
    }

    fun <U>reactive(observable: Observable<U>, initializer: Reactive<T, D>.(U) -> Unit) {
        val reactiveStorage = ReactiveStorage(this, observable, initializer)
        observable.get()?.let {
            reactiveStorage.build(it)
        }

        this.storages.add(reactiveStorage)
    }

    fun addComponent(component: T, extraData: D) {
        if(this.reactiveQueue != null) {
            this.reactiveQueue!!.add(Pair(component, extraData))
        } else {
            this.storages.add(Storage(component, extraData))
        }
    }

    fun getComponents(): Collection<T> = this.storages.flatMap { it -> it.garbage.map { it.first } }
}