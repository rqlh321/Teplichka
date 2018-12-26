package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.Constants
import com.mygdx.game.stage.MainStage

class GameScreen : Screen {

    private val camera = OrthographicCamera()
    private val fitViewport: FitViewport = FitViewport(Constants.VIEW_PORT_WIDTH, Constants.VIEW_PORT_HEIGHT, camera)
    private val mainStage: MainStage = MainStage(camera)

    override fun show() = Unit

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        mainStage.act()
    }

    override fun resize(width: Int, height: Int) =  fitViewport.update(width, height)

    override fun pause() = Unit

    override fun resume() = Unit

    override fun hide() = Unit

    override fun dispose() = mainStage.dispose()

}
