package com.mygdx.game

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

class BodyController(private val body: Body) : InputProcessor {

    private val behavior: Behavior = body.userData as Behavior

    private var x = 0
    private var y = 0

    override fun keyDown(keycode: Int): Boolean = when (keycode) {
        Input.Keys.UP -> {
            if (behavior.jump) body.applyLinearImpulse(0f, UP, body.position.x, body.position.y, true)
            true
        }
        Input.Keys.LEFT -> {
            body.applyLinearImpulse(toLeft, 0f, body.position.x, body.position.y, true)
            true
        }
        Input.Keys.RIGHT -> {
            body.applyLinearImpulse(toRight, 0f, body.position.x, body.position.y, true)
            true
        }
        else -> false
    }

    override fun keyUp(keycode: Int): Boolean = when (keycode) {
        Input.Keys.CONTROL_RIGHT -> true
        Input.Keys.SPACE -> true
        Input.Keys.LEFT -> true
        Input.Keys.RIGHT -> true
        else -> false
    }

    override fun keyTyped(character: Char): Boolean = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val dX = screenX - x
        val dY = screenY - y

        if (dX > 0) {
            body.applyAngularImpulse(-2f, true)
            body.applyLinearImpulse(toRight, 0f, body.position.x, body.position.y, true)
        } else if (dX < 0) {
            body.applyAngularImpulse(2f, true)
            body.applyLinearImpulse(toLeft, 0f, body.position.x, body.position.y, true)
        }
        if (behavior.jump && dY < 0) {
            body.applyLinearImpulse(0f, UP, body.position.x, body.position.y, true)
        }
        x = screenX
        y = screenY
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false

    override fun scrolled(amount: Int): Boolean = false

    companion object {
        private const val UP = .005f
        private const val DOWN = -.001f
        private const val toRight = .00001f
        private const val toLeft = -.00001f
    }
}
