package net.alfheim.tool.internal.kotlinui.kit

import net.alfheim.internal.kotlinui.dsl.label.*
import net.alfheim.internal.kotlinui.kit.Kit
import net.alfheim.tool.internal.kotlinui.kit.Color.BACKGROUND_COLOR
import net.alfheim.tool.internal.kotlinui.kit.Color.BORDER_COLOR
import net.alfheim.tool.internal.kotlinui.kit.Color.INPUT_BACKGROUND_COLOR
import net.alfheim.tool.internal.kotlinui.kit.Color.INPUT_BORDER_COLOR
import net.alfheim.tool.internal.kotlinui.kit.Color.INPUT_FOCUSED_BACKGROUND_COLOR
import net.alfheim.tool.internal.kotlinui.kit.Color.INPUT_FOCUSED_BORDER_COLOR
import net.alfheim.tool.internal.kotlinui.kit.Color.TEXT_COLOR
import net.alfheim.tool.internal.kotlinui.kit.Font.INTER_FONT
import net.minecraft.util.Identifier
import java.awt.Color

val defaultKit = DefaultKit.app

fun<T: Label> defaultKit(initializer: T.() -> Unit): T.() -> Unit = DefaultKit.app(initializer)

object DefaultKit: Kit {
    override fun<T: Label> app(initializer: T.() -> Unit): T.() -> Unit = {
        if(this is Text) {
            this.font = INTER_FONT
            this.color = TEXT_COLOR
        }

        if(this is TextButton) {
            this.padding {
                right = 3u
                left = 3u
                bottom = 1u
                top = 1u
            }
            this.border(1u, INPUT_BORDER_COLOR, BorderRound.medium())

            this.backgroundColor = INPUT_BACKGROUND_COLOR
            this.focusedBackgroundColor = INPUT_FOCUSED_BACKGROUND_COLOR
            this.focusedBorderColor = INPUT_FOCUSED_BORDER_COLOR
        }

        if(this is Container) {
            this.backgroundColor = BACKGROUND_COLOR

            this.padding {
                right = 3u
                left = 3u
                bottom = 1u
                top = 1u
            }
            this.border(1u, BORDER_COLOR, BorderRound.medium())
        }

        this.apply(initializer)
    }
}

object Color {
    @JvmField
    val BACKGROUND_COLOR = Color(0x3c3f41)
    @JvmField
    val BORDER_COLOR = Color(0x323232)
    @JvmField
    val INPUT_BACKGROUND_COLOR = Color(0x4c5052)
    @JvmField
    val INPUT_BORDER_COLOR = Color(0x5e6060)
    @JvmField
    val INPUT_FOCUSED_BACKGROUND_COLOR = Color(0x365880)
    @JvmField
    val INPUT_FOCUSED_BORDER_COLOR = Color(0x4c708c)
    @JvmField
    val TEXT_COLOR = Color(0xbbbbbb)
}

object Font {
    @JvmField
    val INTER_FONT = Identifier("minecraft", "inter")
}