package com.mygdx.game.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.BodyController
import com.mygdx.game.MyContactListener
import com.mygdx.game.Unit
import com.mygdx.game.Util

class MainStage(private val camera: OrthographicCamera) : Stage() {
    private val world: World = World(Vector2(0f, -9.8f), true)
    private val box2DDebugRenderer: Box2DDebugRenderer
    private val player: Body

    init {
        world.setContactListener(MyContactListener())
        player = Util.initBody(world, Unit.PLAYER, Vector2(1f, 1f))

        for (i in 0..4) {
            Util.initBody(world, Unit.ENEMY, Vector2(1f * i, 5f ))
        }
        Util.initPlatform(world)

        Gdx.input.inputProcessor = BodyController(player)
        box2DDebugRenderer = Box2DDebugRenderer()
        box2DDebugRenderer.isDrawVelocities = true
        box2DDebugRenderer.isDrawContacts = true
    }

    override fun act() {
        super.act()
        world.step(1 / 60f, 6, 2)
        Util.clear(world)

        box2DDebugRenderer.render(world, camera.combined)

        cameraWork()
    }

    private fun cameraWork() {
        val dx = player.position.x - camera.position.x
        val cameraX = camera.position.x + dx * .1f

        val dy = player.position.y - camera.position.y
        val cameraY = camera.position.y + dy * .1f

        camera.position.set(cameraX, cameraY, 0f)

//        val velocity = player.linearVelocity.y
//        val dz = 1 + Math.abs(velocity) - camera.zoom
//        val coefficient = if (velocity >= 0) .05f else .01f
//        val cameraZ = camera.zoom + dz * coefficient
//        camera.zoom = cameraZ

        camera.update()
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        box2DDebugRenderer.dispose()
    }

}
