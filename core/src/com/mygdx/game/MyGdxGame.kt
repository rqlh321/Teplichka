package com.mygdx.game

import com.badlogic.gdx.Game
import com.mygdx.game.screen.GameScreen

class MyGdxGame : Game() {
    override fun create() {
        setScreen(GameScreen())

    }
}