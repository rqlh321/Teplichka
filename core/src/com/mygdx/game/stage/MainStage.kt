package com.mygdx.game.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.*
import com.mygdx.game.Unit

class MainStage : Stage() {

    private val player: Body

    val world: World = World(Vector2(0f, -9.8f), true).apply {
        setContactListener(MyContactListener())
    }

    private val lvlManager = LevelMapManager(this)

    private val box2DDebugRenderer: Box2DDebugRenderer = Box2DDebugRenderer().apply {
        isDrawVelocities = true
        isDrawContacts = true
    }

    init {
        viewport = FitViewport(Constants.VIEW_PORT_WIDTH, Constants.VIEW_PORT_HEIGHT, camera)
        player = world.initBody(Unit.PLAYER, lvlManager.startPoint())
        Gdx.input.inputProcessor = BodyController(player)
    }

    override fun act() {
        super.act()
        world.step(1 / 60f, 6, 2)
        world.clear()

        lvlManager.renderBackground()
        box2DDebugRenderer.render(world, camera.combined)
        lvlManager.renderForeground()

        camera.smoothScrollOn(player)
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        box2DDebugRenderer.dispose()
    }

}
