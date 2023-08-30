package net.alfheim.internal.kotlinui.extensions

import net.minecraft.client.font.TextRenderer
import net.minecraft.text.StringVisitable
import net.minecraft.text.Text

fun TextRenderer.javaWrapLinesWithStyle(text: Text, maxWidth: Int): MutableList<StringVisitable>
        = this.textHandler.wrapLines(text, maxWidth, text.style)

fun TextRenderer.wrapLinesWithStyle(text: Text, maxWidth: UInt) = javaWrapLinesWithStyle(text, maxWidth.toInt())