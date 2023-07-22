package me.ggulmool.application.domain.common

object StringUtil {
    const val EMPTY = ""

    fun removeComma(value: String): String {
        return value.replace(",", "")
    }

    fun isNotEmpty(value: String): Boolean {
        return "" != value
    }
}