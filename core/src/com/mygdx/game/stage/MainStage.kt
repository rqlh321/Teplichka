package com.mygdx.game.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.*
import com.mygdx.game.movement.DirectionGestureListener
import com.mygdx.game.unit.Type

class MainStage : Stage() {

    private val player: Body
    private val texture = Texture(Gdx.files.internal("character/luchok/luchok.png"))
    private val batch = SpriteBatch()

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
        player = world.initBody(Type.PLAYER, lvlManager.startPoint())
        Gdx.input.inputProcessor = GestureDetector(DirectionGestureListener(player))
    }

    override fun act() {
        super.act()
        world.act()

        lvlManager.renderBackground()

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(texture, player.position.x - .1f, player.position.y - .1f, .2f, .2f)
        batch.end()

        box2DDebugRenderer.render(world, camera.combined)

        lvlManager.renderForeground()

        camera.leapOnTarget(player)

        if (player.position.y < 0) MyGdxGame.GAME?.create()
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        box2DDebugRenderer.dispose()
    }

}
