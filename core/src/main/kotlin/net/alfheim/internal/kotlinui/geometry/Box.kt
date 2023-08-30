package net.alfheim.internal.kotlinui.geometry

data class Box(val x1: Double, val x2: Double, val y1: Double, val y2: Double) {
    fun inside(x: Double, y: Double) = x in x1..x2 && y in y1..y2
}