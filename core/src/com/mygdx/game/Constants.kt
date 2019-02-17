package com.mygdx.game

import com.badlogic.gdx.math.Vector2

object Constants {
    const val VIEW_PORT_WIDTH = 26.6f
    const val VIEW_PORT_HEIGHT = 16f

    const val MPP = .1f
    const val LEVEL_SCALE = .05f

    const val REDUSE_CAMERA_SPEED = .5f

    val RIGHT = Vector2(10f, 0f)
    val RIGHT_FORCE = Vector2(600f, 0f)
    val LEFT = Vector2(-10f, 0f)
    val LEFT_FORCE = Vector2(-600f, 0f)
    val UP = Vector2(0f, 50f)
    val DOWN = Vector2(0f, -50f)
}
