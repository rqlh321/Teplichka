package com.mygdx.game.stage

import box2dLight.PointLight
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
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
import com.mygdx.game.unit.ActorsRenderer
import com.mygdx.game.unit.Type
import box2dLight.RayHandler
import box2dLight.RayHandler.useDiffuseLight
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FillViewport


class MainStage : Stage() {

    private val player: Body
    private val batch = SpriteBatch()
    val orthographicCamera = camera as OrthographicCamera

    val world: World = World(Vector2(0f, -98f), true).apply {
        setContactListener(MyContactListener())
    }
    private val rayHandler = RayHandler(world).apply {
        useDiffuseLight(true)
        setBlur(true)
        isDebugAll=true
    }
    private val lvlManager = LevelMapManager(this)
    private val actorsRenderer: ActorsRenderer
    private val paralaxRenderer = ParalaxRenderer(orthographicCamera, batch)
    private val box2DDebugRenderer: Box2DDebugRenderer = Box2DDebugRenderer().apply {
        isDrawVelocities = true
        isDrawContacts = true
    }

    init {

        orthographicCamera.zoom = 10f
        viewport = ExtendViewport(Constants.VIEW_PORT_WIDTH, Constants.VIEW_PORT_HEIGHT, camera)
        player = world.initBody(Type.PLAYER, lvlManager.startPoint())
        PointLight(rayHandler,128).attachToBody(player)
        actorsRenderer = ActorsRenderer(player)
        Gdx.input.inputProcessor = GestureDetector(DirectionGestureListener(player))
    }

    override fun act() {
        super.act()
        batch.projectionMatrix = camera.combined

        batch.begin()
        paralaxRenderer.render()
        batch.end()

        lvlManager.mapRenderer.setView(orthographicCamera)
        lvlManager.mapRenderer.render()

        batch.begin()
        actorsRenderer.render(batch)
        batch.end()

        rayHandler.setCombinedMatrix(orthographicCamera)
        rayHandler.updateAndRender()

        box2DDebugRenderer.render(world, camera.combined)


        camera.leapOnTarget(player)

        world.act()
        if (player.position.y < 0) MyGdxGame.GAME?.create()
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        box2DDebugRenderer.dispose()
    }

}
