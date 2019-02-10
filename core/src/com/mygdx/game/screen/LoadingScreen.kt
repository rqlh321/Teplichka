package com.mygdx.game.screen

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.mygdx.game.MyGdxGame

class LoadingScreen(private val game: Game) : Screen {

    init {
        MyGdxGame.ASSET_MANAGER.setLoader<TiledMap, TmxMapLoader.Parameters>(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))
        MyGdxGame.ASSET_MANAGER.load("map/test/map2.tmx", TiledMap::class.java)
    }

    override fun show() = Unit

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        if (MyGdxGame.ASSET_MANAGER.update()) {
            game.screen = GameScreen()
        }
    }

    override fun resize(width: Int, height: Int) = Unit

    override fun pause() = Unit

    override fun resume()  = Unit

    override fun hide()  = Unit

    override fun dispose()  = Unit
}
