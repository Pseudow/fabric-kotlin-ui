package net.alfheim.internal.kotlinui.reactive

import net.alfheim.internal.kotlinui.dsl.label.Label
import java.util.*

/**
 * This class is a storage to store anything.
 * It is used to add an abstract layer to lists allowing them to use reactivity.
 */
open class Storage<T: Label, D> {
    val garbage: MutableList<Pair<T, D>>

    constructor() {
        this.garbage = ArrayList()
    }

    constructor(component: T, data: D) {
        this.garbage = Collections.singletonList(Pair(component, data))
    }
}

/**
 * Used to create a Storage with only basic components
 *
 * @see net.alfheim.mod.hud.api.dsl.label.Container
 */
class SimpleStorage<T: Label>(component: T) : Storage<T, Unit>(component, Unit)

/**
 * This class is used to store the type of label and additional data.
 * Mainly, used in a reactive context.
 */
class ReactiveStorage<U, T: Label, D>(private val reactive: Reactive<T, D>,
                                      private val observable: Observable<U>,
                                      private val initializer: Reactive<T, D>.(U) -> Unit): Storage<T, D>() {
    init {
        this.observable.subscribe {
            this.build(it)
        }
    }

    fun build(it: U) {
        this.garbage.clear()
        this.garbage.addAll(this.reactive.invoke(it, this.initializer))
    }
}