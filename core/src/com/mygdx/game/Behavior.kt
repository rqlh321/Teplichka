package com.mygdx.game

class Behavior( val unit: Unit) {
    var jump = true
    var alive = true

    fun isEnemyTo(other: Behavior): Boolean = this.unit != other.unit && this.unit != Unit.PLATFORM && other.unit != Unit.PLATFORM
}
