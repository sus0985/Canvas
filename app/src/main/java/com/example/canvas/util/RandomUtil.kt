package com.example.canvas.util

object RandomUtil {
    private const val RANDOM_ID = "abcdefghijklmnopqrstuvwxyz0123456789"
    private const val RANDOM_COLOR = "0123456789ABCDEF"

    fun getRandomID(): String {
        val sb = StringBuilder()
        repeat(3) { sb.append(RANDOM_ID[RANDOM_ID.indices.random()]) }
        sb.append("-")
        repeat(3) { sb.append(RANDOM_ID[RANDOM_ID.indices.random()]) }
        sb.append("-")
        repeat(3) { sb.append(RANDOM_ID[RANDOM_ID.indices.random()]) }
        return sb.toString()
    }

    fun getRandomColor(): String {
        val sb = StringBuilder()
        repeat(6) { sb.append(RANDOM_COLOR[RANDOM_COLOR.indices.random()]) }
        return sb.toString()
    }
}
