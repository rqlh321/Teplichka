package com.mygdx.game.stage

import box2dLight.RayHandler
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.*
import com.mygdx.game.Unit

class MainStage(private val camera: OrthographicCamera) : Stage() {

    private val player: Body
    private val world: World = World(Vector2(0f, -9.8f), true).apply {
        setContactListener(MyContactListener())
    }

    private val rayHandler = RayHandler(world).apply {
        setAmbientLight(0.1f, 0.1f, 0.1f, 1f)
        setBlurNum(3)
        setShadows(true)
    }
    private val box2DDebugRenderer: Box2DDebugRenderer = Box2DDebugRenderer().apply {
        isDrawVelocities = true
        isDrawContacts = true
    }

    init {
        world.initPlatform()
        player = world.initBody(rayHandler, Unit.PLAYER, Vector2(1f, 1f))
        (0..4).forEach { world.initBody(rayHandler, Unit.ENEMY, Vector2(1f * it, 5f)) }
        Gdx.input.inputProcessor = BodyController(player)
    }

    override fun act() {
        super.act()
        world.step(1 / 60f, 6, 2)
        world.clear()

        box2DDebugRenderer.render(world, camera.combined)

        rayHandler.setCombinedMatrix(camera)
        rayHandler.updateAndRender()

        camera.smoothScrollOn(player)
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        box2DDebugRenderer.dispose()
    }

}
