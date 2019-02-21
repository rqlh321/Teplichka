package com.mygdx.game.unit

data class Entity(
        val type: Type,
        var onGround: Boolean = true,
        var alive: Boolean = true,
        var extraForce: Boolean = true,
        var atack: Boolean = false
) {
    companion object {
        const val HEAD = "head"
        const val CORE = "core"
        const val FOOT = "foot"
    }
}