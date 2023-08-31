package net.alfheim.internal.kotlinui.dsl.label

import net.alfheim.internal.kotlinui.event.EventListener
import net.alfheim.internal.kotlinui.event.EventTypes
import net.minecraft.util.Identifier
import java.awt.Color
import java.util.*

/**
 * Labels are the main core objects of all displayable components that can be rendered.
 * It contains basic properties that can also be found in every **HTML** component.
 */
abstract class Label {
    /**
     * Allows adding custom events on the label such as **interactions** with the *mouse*.
     * @see EventTypes
     */
    val listeners: MutableMap<EventTypes, MutableCollection<EventListener>> = EnumMap(EventTypes::class.java)
    var visible: Boolean = true

    /**
     * @see Border
     */
    var border: Border? = null
    /**
     * @see Margin
     */
    var margin: Margin? = null
    /**
     * @see Padding
     */
    var padding: Padding = Padding()

    open var width: UInt? = null
    open var height: UInt? = null

    var maxWidth: UInt? = null
    var maxHeight: UInt? = null

    /**
     * This parameter is used by label's subclasses.
     * It, in a certain way, allows the label to grow identically
     * to its container growth.
     */
    abstract val growable: Boolean

    /**
     * X Pos represents the position of the label within the player screen.
     * This value is computed and **any change will have no effect**.
     */
    var xPos: Float = 0f
    /**
     * Y Pos represents the position of the label within the player screen.
     * This value is computed and **any change will have no effect**.
     */
    var yPos: Float = 0f

    /**
     * Offset width corresponds to the width calculated including
     * margin, padding and border.
     */
    var offsetWidth: UInt = 0u
    /**
     * Offset height corresponds to the width calculated including
     * margin, padding and border.
     */
    var offsetHeight: UInt = 0u

    /**
     * Allow **vertical scrolling** on the container if the content exceeds the **maximum width** specified.
     */
    var scrollableX: Boolean = false
    /**
     * Allow **horizontal scrolling** on the container if the content exceeds the **maximum height** specified.
     */
    var scrollableY: Boolean = false

    var backgroundColor: Color? = null
    var backgroundImage: Identifier? = null

    init {
        EventTypes.entries.forEach {
            this.listeners[it] = ArrayList()
        }
    }

    fun bind(eventType: EventTypes, listener: EventListener): Label {
        this.listeners[eventType]?.add(listener)
        return this
    }

    fun totalWidth(): UInt {
        if(this.maxWidth != null)
            return this.maxWidth!!

        return this.offsetWidth + (this.width ?: 0u)
    }

    fun totalHeight(): UInt {
        var total = this.offsetHeight

        total += if(this.maxHeight != null)
            this.maxHeight!!
        else this.height ?: 0u

        return total
    }

    fun border(total: UShort) = this.border(this.initSpacing(total))

    fun border(total: UShort, borderColor: Color) = this.border {
        this.borderColor = borderColor

        this.apply(initSpacing(total))
    }

    fun border(total: UShort, borderColor: Color, borderRound: BorderRound) = this.border {
        this.borderColor = borderColor
        this.borderRound = borderRound

        this.apply(initSpacing(total))
    }

    fun border(initializer: Border.() -> Unit) {
        this.border = Border().apply(initializer)
    }

    fun margin(total: UShort) = this.margin(this.initSpacing(total))

    fun margin(initializer: Margin.() -> Unit) {
        this.margin = Margin().apply(initializer)
    }

    fun padding(total: UShort) = this.padding(this.initSpacing(total))

    fun padding(initializer: Padding.() -> Unit) {
        this.padding = Padding().apply(initializer)
    }

    private fun initSpacing(total: UShort): InternalSpacing.() -> Unit = {
        this.top = total
        this.bottom = total
        this.right = total
        this.left = total
    }

    fun inside(x: Float, y: Float): Boolean {
        val x1 = this.xPos
        val x2 = this.xPos + this.totalWidth().toFloat()
        val y1 = this.yPos
        val y2 = this.yPos + this.totalHeight().toFloat()

        return x in x1..x2 && y in y1..y2
    }
}