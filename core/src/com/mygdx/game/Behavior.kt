package com.mygdx.game

import com.badlogic.gdx.physics.box2d.Body
import java.util.*
import kotlin.collections.HashSet

data class Behavior(val unit: Unit, var onGround:Boolean = true, var alive:Boolean = true) {


    val connectTo = HashSet<Body>()
    val disconnectFrom = LinkedList<Body>()
    var extraForce = true

    fun isEnemyTo(other: Behavior): Boolean = this.unit != other.unit && this.unit != Unit.PLATFORM && other.unit != Unit.PLATFORM

}