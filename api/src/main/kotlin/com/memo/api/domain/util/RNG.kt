package com.memo.api.domain.util

import com.fasterxml.uuid.Generators
import org.springframework.stereotype.Component
import java.util.UUID

@Component
object RNG {
    fun generateKey(): String {
        val tokens = UUID.randomUUID().toString().split("-")
        return tokens[2] + tokens[1] + tokens[0] + tokens[3] + tokens[4]
    }
}

fun main() {
    val timeBasedMap = mutableMapOf<Int, String>()
    repeat(10) { i ->
        timeBasedMap[i] = Generators.timeBasedGenerator().generate().toString()
        println("$i: ${timeBasedMap[i]}")
    }
    println()

    val sortedTimeBasedMap =
        timeBasedMap.toSortedMap { it, other -> timeBasedMap[it]!!.compareTo(timeBasedMap[other]!!) }
    for (key in sortedTimeBasedMap.keys) {
        println("$key: ${timeBasedMap[key]}")
    }
}
