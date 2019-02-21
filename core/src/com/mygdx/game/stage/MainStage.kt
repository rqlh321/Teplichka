package com.mygdx.game.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.mygdx.game.*
import com.mygdx.game.movement.DirectionGestureListener
import com.mygdx.game.movement.MyContactListener
import com.mygdx.game.screen.LoadingScreen.Companion.TEST_LEVEL
import com.mygdx.game.unit.Type

class MainStage : Stage() {

    private val world: World = World(Vector2(0f, -98f), true).apply {
        setContactListener(MyContactListener())
    }
    private val box2DDebugRenderer: Box2DDebugRenderer = Box2DDebugRenderer().apply {
        isDrawVelocities = true
        isDrawContacts = true
    }

    private var tiledMap: TiledMap = MyGdxGame.ASSET_MANAGER.get(TEST_LEVEL)
    private val mapRenderer = OrthogonalTiledMapRenderer(tiledMap, Constants.MPP)

    private val player: Body

    init {
        viewport = ExtendViewport(Constants.VIEW_PORT_WIDTH, Constants.VIEW_PORT_HEIGHT, camera)

        tiledMap.createPlatformsBody(world)

        player = world.initBody(Type.PLAYER, tiledMap.playerPosition())
        Gdx.input.inputProcessor = GestureDetector(DirectionGestureListener(player))
    }

    override fun act() {
        super.act()
        world.act()
        if (player.position.y < 0) MyGdxGame.GAME?.create()

        mapRenderer.setView(camera as OrthographicCamera)
        mapRenderer.render()

        box2DDebugRenderer.render(world, camera.combined)

        camera.smoothScrollOn(player)
    }

}
