package com.mygdx.game

import com.badlogic.gdx.math.Vector2

object Constants {
    val VIEW_PORT_WIDTH = 2.66f
    val VIEW_PORT_HEIGHT = 1.6f

    val SCALE = .1f
    val LEVEL_SCALE = .05f

    const val SWIPE_LENGTH = 100
    val RIGHT = Vector2(1f, 0f)
    val RIGHT_FORCE = Vector2(300f, 0f)
    val LEFT = Vector2(-1f, 0f)
    val LEFT_FORCE = Vector2(-300f, 0f)
    val UP = Vector2(0f, 4f)
    val DOWN = Vector2(0f, -4f)
    const val REDUSE_CAMERA_SPEED = .5f
}
