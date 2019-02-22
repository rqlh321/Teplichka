package com.mygdx.game

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.mygdx.game.unit.Entity
import com.mygdx.game.unit.Type

fun World.act() {
    step(1 / 60f, 6, 2)

    val bodyArray = Array<Body>()
    getBodies(bodyArray)
    bodyArray.forEach { body ->
        val entity = body.userData as Entity
        if (!entity.alive) {
            destroyBody(body)
        }
    }
}

fun World.initBody(type: Type, position: Vector2): Body {
    val bodyDef = BodyDef()
    bodyDef.type = BodyDef.BodyType.DynamicBody
    bodyDef.position.set(position)
    bodyDef.fixedRotation = true

    val body = createBody(bodyDef)
    body.userData = Entity(type)
//core
    val coreFixtureDef = FixtureDef()
    coreFixtureDef.restitution = 0f
    coreFixtureDef.density = 0f
    coreFixtureDef.friction = 0f

    val coreShape = PolygonShape()
    coreShape.set(floatArrayOf(
            -.8f, -.7f,
            -.7f, -.8f,
            .7f, -.8f,
            .8f, -.7f,
            .8f, .7f,
            .7f, .8f,
            -.7f, .8f,
            -.8f, .7f
    ))
    coreFixtureDef.shape = coreShape

    val coreFixture = body.createFixture(coreFixtureDef)
    coreFixture.userData = Entity.CORE

    coreShape.dispose()
//foot
    val footFixtureDef = FixtureDef()

    val footShape = PolygonShape()
    footShape.setAsBox(.6f, .1f, Vector2(0f, -.9f), 0f)
//    footShape.set(floatArrayOf(
//            -.7f, -.9f,
//            .7f,-.9f,
//            .8f, -.9f
//    ))
    footFixtureDef.shape = footShape

    val footFixture = body.createFixture(footFixtureDef)
    footFixture.userData = Entity.FOOT
    footFixture.isSensor = true

    footShape.dispose()
// head
    val headFixtureDef = FixtureDef()

    val headShape = PolygonShape()
    headShape.setAsBox(.6f, .1f, Vector2(0f, .9f), 0f)
    headFixtureDef.shape = headShape

    val headFixture = body.createFixture(headFixtureDef)
    headFixture.userData = Entity.HEAD
    headFixture.isSensor = true

    headShape.dispose()
    return body
}

fun Camera.smoothScrollOn(body: Body) {
    val dx = body.position.x - position.x
    val cameraX = position.x + dx * .2f

    val dy = body.position.y - position.y
    val cameraY = position.y + dy * .2f

    position.set(cameraX, cameraY, 0f)

    //        val velocity = player.linearVelocity.y
//        val dz = 1 + Math.abs(velocity) - camera.zoom
//        val coefficient = if (velocity >= 0) .05f else .01f
//        val cameraZ = camera.zoom + dz * coefficient
//        camera.zoom = cameraZ

    update()
}

fun TiledMap.createPlatformsBody(world: World) {

    val layer = this.layers.get("platforms") as TiledMapTileLayer

    val shifting = .5f

    val perCellWidth = layer.tileWidth * Constants.MPP
    val perCellHeight = layer.tileHeight * Constants.MPP

    (0..layer.height).forEach { hIndex ->
        var startPoint = -1
        (0..layer.width).forEach { wIndex ->
            if (layer.getCell(wIndex, hIndex) != null) {
                if (startPoint == -1) {
                    startPoint = wIndex
                }
            } else if (startPoint != -1) {
                val countOfCells = wIndex - startPoint
                val hx = countOfCells * perCellWidth * .5f
                val hy = perCellHeight * .5f

                val x = (startPoint + countOfCells / 2f) * perCellWidth
                val y = (hIndex + shifting) * perCellHeight

                val groundBodyDef = BodyDef()
                groundBodyDef.position.set(Vector2(x, y))
                val groundBody = world.createBody(groundBodyDef)
                groundBody.userData = Entity(Type.PLATFORM)
                val groundBox = PolygonShape()
                groundBox.setAsBox(hx, hy)
                val groundFixture = groundBody.createFixture(groundBox, 0.0f)
                groundFixture.userData = Entity.GROUND
                groundBox.dispose()

                startPoint = -1
            }
        }
    }
}

fun TiledMap.createPlatformsBoxBody(world: World) {

    val layer = this.layers.get("platforms") as TiledMapTileLayer

    val shifting = .5f

    val perCellWidth = layer.tileWidth * Constants.MPP
    val perCellHeight = layer.tileHeight * Constants.MPP

    (0..layer.height).forEach { hIndex ->
        (0..layer.width).forEach { wIndex ->
            if (layer.getCell(wIndex, hIndex) != null) {

                val hx = perCellWidth * .5f
                val hy = perCellHeight * .5f

                val x = (wIndex + shifting) * perCellWidth
                val y = (hIndex + shifting) * perCellHeight

                val bodyDef = BodyDef()
                bodyDef.position.set(Vector2(x, y))

                val body = world.createBody(bodyDef)
                body.userData = Entity(Type.PLATFORM)

                val shape = PolygonShape()
                shape.setAsBox(hx, hy)

                val fixtureDef = FixtureDef()
                fixtureDef.shape = shape
                fixtureDef.restitution = 0f
                fixtureDef.density = 0f
                fixtureDef.friction = 0f

                body.createFixture(fixtureDef)

                shape.dispose()
            }
        }
    }
}

fun TiledMapTileLayer.positions(): List<Vector2> {
    val result = ArrayList<Vector2>()
    val perCellWidth = tileWidth * Constants.MPP
    val perCellHeight = tileHeight * Constants.MPP

    val shifting = .5f

    (0..height).forEach { hIndex ->
        (0..width).forEach { wIndex ->
            if (getCell(wIndex, hIndex) != null) {
                val centerW = wIndex + shifting
                val x = centerW * perCellWidth

                val centerH = hIndex + shifting
                val y = centerH * perCellHeight

                val position = Vector2(x, y)

                result.add(position)
            }
        }
    }
    return result
}

fun TiledMap.playerPosition(): Vector2 {
    val point = (layers.get("player") as TiledMapTileLayer).positions().first()
    return point
}