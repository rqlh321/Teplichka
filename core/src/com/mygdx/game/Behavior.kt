package com.mygdx.game

import com.badlogic.gdx.physics.box2d.Body
import java.util.*
import kotlin.collections.HashSet

class Behavior(val unit: Unit) {

    var jump = true
    var alive = true

    val connectTo = HashSet<Body>()
    val disconnectFrom = LinkedList<Body>()

    fun isEnemyTo(other: Behavior): Boolean = this.unit != other.unit && this.unit != Unit.PLATFORM && other.unit != Unit.PLATFORM

    fun updateJump(other: Behavior, availability: Boolean) {
        if (other.unit == Unit.PLATFORM) {
            jump = availability
        }
    }
}