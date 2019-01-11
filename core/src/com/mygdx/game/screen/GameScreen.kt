package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.mygdx.game.stage.MainStage

class GameScreen : Screen {

    private val mainStage: MainStage = MainStage()

    override fun show() = Unit

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        mainStage.act()
    }

    override fun resize(width: Int, height: Int) =  mainStage.viewport.update(width, height)

    override fun pause() = Unit

    override fun resume() = Unit

    override fun hide() = Unit

    override fun dispose() = mainStage.dispose()

}
