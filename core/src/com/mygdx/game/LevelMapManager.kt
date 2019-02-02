package com.mygdx.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.objects.PolylineMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.ChainShape
import com.badlogic.gdx.physics.box2d.World
import com.mygdx.game.stage.MainStage
import com.mygdx.game.unit.Entity
import com.mygdx.game.unit.Type

/**
 * Created by sic on 20.01.2017.
 */

class LevelMapManager(mainStage: MainStage) {

    var tiledMap: TiledMap = MyGdxGame.ASSET_MANAGER.get("map/second/map2.tmx")

    private val layerPlatformBody: TiledMapTileLayer
    private val playerPositions: MapLayer

    private val world: World = mainStage.world

    val mapRenderer:OrthogonalTiledMapRenderer
    init {

        val mapSizes = tiledMap.getSize()

        worldWidth = mapSizes[0]
        worldHeight = mapSizes[1]

        val mapLayers = tiledMap.layers
        layerPlatformBody = mapLayers.get("platforms") as TiledMapTileLayer
        layerPlatformBody.createStaticBody(world)
        playerPositions = mapLayers.get("player_positions")

        mapRenderer= OrthogonalTiledMapRenderer(tiledMap,Constants.SCALE)

    }

    private fun initPlatforms(world: World) {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        val platform = world.createBody(bodyDef)
        platform.userData = Entity(Type.PLATFORM)

        val worldObjects = layerPlatformBody.objects
        for (objectWorld in worldObjects) {
            val vertices = (objectWorld as PolylineMapObject).polyline.transformedVertices
            val worldVertices = arrayOfNulls<Vector2>(vertices.size / 2)
            for (j in worldVertices.indices) {
                worldVertices[j] = Vector2(vertices[j * 2] * Constants.SCALE, vertices[j * 2 + 1] * Constants.SCALE)
            }
            val cs = ChainShape()
            cs.createChain(worldVertices)
            platform.createFixture(cs, 0f)
            cs.dispose()
        }
    }

    fun startPoint(): Vector2 {
        val playerPosition = Vector2()
        val positions = playerPositions.objects
        (positions.get("start_point") as RectangleMapObject)
                .rectangle
                .getPosition(playerPosition)
        playerPosition.set(playerPosition.x * Constants.SCALE, playerPosition.y * Constants.SCALE)
        return playerPosition
    }

    companion object {

        var worldWidth = 0f
        var worldHeight = 0f
    }

}