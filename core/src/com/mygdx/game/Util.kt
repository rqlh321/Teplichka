package com.mygdx.game

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.mygdx.game.LevelMapManager.Companion.worldWidth
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

fun World.initPlatform(): Body {
    val bodyDef = BodyDef()
    bodyDef.type = BodyDef.BodyType.StaticBody
    bodyDef.position.set(Vector2(0f, 0f))
    bodyDef.fixedRotation = false
    val body = createBody(bodyDef)
    body.userData = Entity(Type.PLATFORM)
    val bodyShape = ChainShape()
    val chain = FloatArray(10)
    chain.forEachIndexed { index, _ ->
        chain[index] = if (index % 2 == 0) index * 5f else Math.random().toFloat()
    }
    bodyShape.createChain(chain)
    val fixtureDefBody = FixtureDef()
    fixtureDefBody.shape = bodyShape
    body.createFixture(fixtureDefBody)
    bodyShape.dispose()
    return body
}

fun World.initBody(type: Type, position: Vector2): Body {
    val bodyDef = BodyDef()
    bodyDef.type = BodyDef.BodyType.DynamicBody
    bodyDef.position.set(position)
    bodyDef.fixedRotation = true

    val body = createBody(bodyDef)
    body.userData = Entity(type)

    val fixtureDefFoots = FixtureDef()

    when (type) {
        Type.ENEMY -> {
            val circleShape = CircleShape()
            circleShape.radius = Math.random().toFloat() * .1f

            fixtureDefFoots.restitution = Math.random().toFloat() * .1f
            fixtureDefFoots.density = Math.random().toFloat() * .1f
            fixtureDefFoots.shape = circleShape

            body.createFixture(fixtureDefFoots)

            circleShape.dispose()
        }
        Type.PLAYER -> {
            val box = PolygonShape()
            box.setAsBox(.8f, .8f)

            fixtureDefFoots.restitution = 0f
            fixtureDefFoots.density = 0f
            fixtureDefFoots.friction = 0f
            fixtureDefFoots.shape = box

            body.createFixture(fixtureDefFoots)

            box.dispose()
        }
    }


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

fun Camera.leapOnTarget(body: Body) {
    if (body.position.x - viewportWidth / 2 > 0 && body.position.x + viewportWidth / 2 < worldWidth) {
        if (body.position.y > viewportHeight / 2) {
            position.set(
                    position.x + (body.position.x - position.x) * Constants.REDUSE_CAMERA_SPEED,
                    position.y + (body.position.y - position.y) * Constants.REDUSE_CAMERA_SPEED,
                    0f)
        } else {
            position.set(
                    position.x + (body.position.x - position.x) * Constants.REDUSE_CAMERA_SPEED,
                    position.y + (viewportHeight / 2 - position.y) * Constants.REDUSE_CAMERA_SPEED,
                    0f)
        }
    } else {
        if (body.position.y > viewportHeight / 2) {
            position.set(
                    position.x,
                    position.y + (body.position.y - position.y) * Constants.REDUSE_CAMERA_SPEED,
                    0f)
        } else {
            if (body.position.x < viewportWidth / 2) {
                position.set(
                        position.x + (viewportWidth / 2 - position.x) * Constants.REDUSE_CAMERA_SPEED,
                        position.y + (viewportHeight / 2 - position.y) * Constants.REDUSE_CAMERA_SPEED,
                        0f)
            } else {
                position.set(
                        position.x + (worldWidth - viewportWidth / 2 - position.x) * Constants.REDUSE_CAMERA_SPEED,
                        position.y + (viewportHeight / 2 - position.y) * Constants.REDUSE_CAMERA_SPEED,
                        0f)
            }
        }
    }
//    camera.zoom = camera.zoom + (1 + Math.abs(body.linearVelocity.x) * .05f - camera.zoom) * .01f
    update()
}

fun TiledMap.getSize(): FloatArray {
    val prop = properties
    val mapWidth = prop.get("width", Int::class.java)
    val mapHeight = prop.get("height", Int::class.java)
    val tilePixelWidth = prop.get("tilewidth", Int::class.java)
    val tilePixelHeight = prop.get("tileheight", Int::class.java)

    val mapPixelWidth = mapWidth * tilePixelWidth.toFloat()
    val mapPixelHeight = mapHeight * tilePixelHeight.toFloat()

    return floatArrayOf(mapPixelWidth, mapPixelHeight)
}

fun TiledMapTileLayer.createStaticBody(world: World) {

    val padding = .1f

    val shifting = .5f

    val perCellWidth = tileWidth * Constants.MPP
    val perCellHeight = tileHeight * Constants.MPP

    (0..height).forEach { hIndex ->
        var startPoint = -1
        (0..width).forEach { wIndex ->
            if (getCell(wIndex, hIndex) != null) {
                if (startPoint == -1) {
                    startPoint = wIndex
                }
            } else if (startPoint != -1) {
                val countOfCells = wIndex - startPoint
                val hx = countOfCells * perCellWidth * .5f - padding
                val hy = perCellHeight * .5f

                val x = (startPoint + countOfCells / 2f) * perCellWidth
                val y = (hIndex + shifting) * perCellHeight

                val groundBodyDef = BodyDef()
                groundBodyDef.position.set(Vector2(x, y))
                val groundBody = world.createBody(groundBodyDef)
                groundBody.userData = Entity(Type.PLATFORM)
                val groundBox = PolygonShape()
                groundBox.setAsBox(hx, hy)
                groundBody.createFixture(groundBox, 0.0f)
                groundBox.dispose()

                startPoint = -1
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