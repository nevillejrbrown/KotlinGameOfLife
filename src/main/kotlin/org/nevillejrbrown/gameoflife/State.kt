package org.nevillejrbrown.gameoflife

enum class State(val label:String) {
    DEAD("o"),
    ALIVE ("*");

    override fun toString(): String {
        return label
    }
}