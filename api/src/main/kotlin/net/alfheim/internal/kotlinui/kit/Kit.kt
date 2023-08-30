package net.alfheim.internal.kotlinui.kit

import net.alfheim.internal.kotlinui.dsl.label.Label

interface Kit {
    val app: Label.() -> Unit
        get() = app {}

    fun<T: Label> app(initializer: T.() -> Unit): T.() -> Unit
}