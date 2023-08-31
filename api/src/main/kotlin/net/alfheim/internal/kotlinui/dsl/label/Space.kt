package net.alfheim.internal.kotlinui.dsl.label

/**
 * Spaces are used to add **some space between different components**.
 * Even though they can be replaced using *top-margin* on components.
 *
 * @see Margin
 * @see Label
 */
data class Space(val flex: Flex): Label() {
    override val growable: Boolean = false

    /**
     * The space represented, like what did you expect?
     */
    var value: UInt = 0u
}