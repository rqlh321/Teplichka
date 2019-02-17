package com.mygdx.game.unit

data class Entity(
        val type: Type,
        var onGround: Boolean = true,
        var alive: Boolean = true,
        var extraForce: Boolean = true,
        var atack: Boolean = false
)