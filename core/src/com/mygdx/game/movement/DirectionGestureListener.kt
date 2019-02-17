package com.mygdx.game.movement

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.physics.box2d.Body
import com.mygdx.game.Constants
import com.mygdx.game.unit.Entity

class DirectionGestureListener(private val body: Body) : GestureDetector.GestureAdapter() {

    private val entity = body.userData as Entity

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                onRight()
            } else {
                onLeft()
            }
        } else {
            if (velocityY > 0) {
                onDown()
            } else {
                onUp()
            }
        }
        return super.fling(velocityX, velocityY, button)
    }

    private fun onUp() {
        if (entity.onGround) {
            body.linearVelocity = Constants.UP.apply { x = body.linearVelocity.x }
        }
    }

    private fun onDown() {
        if (!entity.onGround) {
            body.linearVelocity = Constants.DOWN
            entity.atack = true
        }
    }

    private fun onLeft() {
        if (entity.onGround) {
            body.linearVelocity = Constants.LEFT
        } else if (entity.extraForce) {
            entity.extraForce = false
            body.applyForceToCenter(Constants.LEFT_FORCE, true)
        }
    }

    private fun onRight() {
        if (entity.onGround) {
            body.linearVelocity = Constants.RIGHT
        } else if (entity.extraForce) {
            entity.extraForce = false
            body.applyForceToCenter(Constants.RIGHT_FORCE, true)
        }
    }

}