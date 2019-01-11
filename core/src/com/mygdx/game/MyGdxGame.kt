package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.mygdx.game.screen.LoadingScreen

class MyGdxGame : Game() {

    override fun create() = setScreen(LoadingScreen(this))

    companion object {

        val ASSET_MANAGER = AssetManager()

    }
}