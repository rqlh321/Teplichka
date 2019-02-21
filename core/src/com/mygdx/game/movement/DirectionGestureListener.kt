package com.mygdx.game.movement

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.physics.box2d.Body
import com.mygdx.game.Constants
import com.mygdx.game.unit.Entity

class DirectionGestureListener(private val body: Body) : GestureDetector.GestureAdapter() {

    private val entity = body.userData as Entity

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        if (entity.onGround) {
            body.linearVelocity = Constants.UP.apply { this.x = body.linearVelocity.x }
        } else {
            body.linearVelocity = Constants.DOWN
            entity.atack = true
        }
        return super.tap(x, y, count, button)
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            body.linearVelocity = body.linearVelocity.apply { x = velocityX/100 }
        }
        return super.fling(velocityX, velocityY, button)
    }


}