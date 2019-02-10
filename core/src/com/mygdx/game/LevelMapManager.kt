package com.mygdx.game

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.mygdx.game.stage.MainStage

/**
 * Created by sic on 20.01.2017.
 */

class LevelMapManager(mainStage: MainStage) {

    var tiledMap: TiledMap = MyGdxGame.ASSET_MANAGER.get("map/test/map2.tmx")


    private val world: World = mainStage.world

    val mapRenderer: OrthogonalTiledMapRenderer

    init {
        val mapSizes = tiledMap.getSize()

        worldWidth = mapSizes[0]
        worldHeight = mapSizes[1]

        (tiledMap.layers.get("platforms") as TiledMapTileLayer).createStaticBody(world)

        mapRenderer = OrthogonalTiledMapRenderer(tiledMap, Constants.MPP)
    }

    fun startPoint(): Vector2 {
        val point = (tiledMap.layers.get("player") as TiledMapTileLayer).positions().first()
        return point
    }

    companion object {
        var worldWidth = 0f
        var worldHeight = 0f
    }

}