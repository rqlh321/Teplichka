package com.mygdx.game

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.physics.box2d.Body

class BodyController(private val body: Body) : InputProcessor {

    private val behavior: Behavior = body.userData as Behavior

    private var x = 0
    private var y = 0

    private var dX = 0
    private var dY = 0

    override fun keyDown(keycode: Int): Boolean = false

    override fun keyUp(keycode: Int) = false

    override fun keyTyped(character: Char) = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int) = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (dX > 0) {
            body.linearVelocity = Constants.toLeft
        } else if (dX < 0) {
            body.linearVelocity = Constants.toRight
        }

        if (dY < 0) {
            if (behavior.jump) {
                body.linearVelocity = Constants.UP.apply { x = body.linearVelocity.x }
            }
        } else {
//            body.linearVelocity = Constants.STOP
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {

        val tmpDx = screenX - x
        dX = if (Math.abs(tmpDx) > 5) tmpDx else 0
        val tmpDy = screenY - y
        dY = if (Math.abs(tmpDy) > 5) tmpDx else 0

        x = screenX
        y = screenY


        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false

    override fun scrolled(amount: Int): Boolean = false
}
