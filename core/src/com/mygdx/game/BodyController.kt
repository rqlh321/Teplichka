package com.mygdx.game

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.physics.box2d.Body

class BodyController(private val body: Body) : InputProcessor {

    private val behavior: Behavior = body.userData as Behavior

    private var x = 0
    private var y = 0

    override fun keyDown(keycode: Int): Boolean = false

    override fun keyUp(keycode: Int): Boolean = false

    override fun keyTyped(character: Char): Boolean = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val dX = screenX - x
        val dY = screenY - y

        if (dX > 0) {
            body.angularVelocity = Constants.toLeft
        } else if (dX < 0) {
            body.angularVelocity = Constants.toRight
        }
        if (behavior.jump && dY < 0) {
//            body.applyLinearImpulse(0f, UP, body.position.x, body.position.y, true)
        }
        x = screenX
        y = screenY
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false

    override fun scrolled(amount: Int): Boolean = false
}
