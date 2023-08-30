package net.alfheim.internal.kotlinui.extensions

import org.apache.commons.lang3.tuple.MutablePair
import kotlin.math.min

typealias Dimensions = MutablePair<UInt, UInt>

fun Dimensions.max(dimensions: Dimensions): Dimensions {
    val first = kotlin.math.max(this.left, dimensions.left)
    val second = kotlin.math.max(this.right, dimensions.right)

    return Dimensions(
        first,
        second
    )
}

fun minExcept(first: UInt, second: UInt, except: UInt): UInt {
    if(first == except)
        return second

    if(second == except)
        return first

    return min(first, second)
}

operator fun Int.times(uInt: UInt) = this.toUInt().times(uInt)

operator fun UInt.times(int: Int) = this.times(int.toUInt())

operator fun Float.times(uInt: UInt) = this.times(uInt.toInt())

operator fun UInt.times(float: Float) = this.toInt().times(float)

operator fun Float.div(uInt: UInt) = this.div(uInt.toInt())

operator fun UInt.div(float: Float): Float = this.toInt().div(float)

operator fun Double.plus(uInt: UInt) = this.plus(uInt.toInt())

operator fun UInt.plus(double: Double): UInt = this.plus(double.toUInt())

operator fun Int.minus(uInt: UInt) = this.minus(uInt.toInt())

operator fun UInt.minus(double: Double) = this.toInt().minus(double)