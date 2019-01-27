package com.mygdx.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapLayers
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.objects.PolylineMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.ChainShape
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import com.mygdx.game.stage.MainStage

/**
 * Created by sic on 20.01.2017.
 */

class LevelMapManager(mainStage: MainStage) {
    var tiledMap: TiledMap = MyGdxGame.ASSET_MANAGER.get("map/second/map0.tmx")
    private val tiledMapRenderer: ParallaxTiledMapRenderer
    private val layerBackgroundZero: MapLayer
    private val layerBackgroundFirst: MapLayer
    private val layerBackgroundSecond: MapLayer
    private val layerForeground: MapLayer
    private val layerPlatformBody: MapLayer
    private val playerPositions: MapLayer
//    val enemiesLayer: MapLayer

    private val camera: OrthographicCamera = mainStage.viewport.camera as OrthographicCamera
    private val world: World = mainStage.world

    init {

        val mapSizes = tiledMap.getSize()

        worldWidth = mapSizes[0]
        worldHeight = mapSizes[1]

        val mapLayers = tiledMap.layers
        layerBackgroundZero = mapLayers.get("background_0")
        layerBackgroundFirst = mapLayers.get("background_1")
        layerBackgroundSecond = mapLayers.get("background_2")
        layerForeground = mapLayers.get("foreground")
        layerPlatformBody = mapLayers.get("platforms")
        playerPositions = mapLayers.get("player_positions")
//        enemiesLayer = mapLayers.get("enemies")

        initPlatforms(world)

        tiledMapRenderer = ParallaxTiledMapRenderer(tiledMap, camera)
    }

    private fun initPlatforms(world: World) {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        val platform = world.createBody(bodyDef)
        platform.userData = Behavior(Unit.PLATFORM)

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

    //    public Body endPointBody() {
    //        Rectangle rectangle = ((RectangleMapObject) playerPositions.getObjects().get("end_point")).getRectangle();
    //        Vector2 endPoint = new Vector2();
    //        rectangle.getPosition(endPoint);
    //        endPoint.set(endPoint.x , endPoint.y );
    //
    //        BodyDef bodyDef = new BodyDef();
    //        bodyDef.type = BodyDef.BodyType.StaticBody;
    //        bodyDef.position.set(endPoint.x + rectangle.getWidth() / 2 , endPoint.y + rectangle.getHeight() / 2 );
    //
    //        Body endPointBody = world.createBody(bodyDef);
    //        endPointBody.setUserData("endPointBody");
    //
    //        PolygonShape shape = new PolygonShape();
    //        shape.setAsBox(rectangle.getWidth() / 2 , rectangle.getHeight() / 2 );
    //
    //        FixtureDef fixtureDef = new FixtureDef();
    //        fixtureDef.density = 0;
    //        fixtureDef.isSensor = true;
    //        fixtureDef.shape = shape;
    //        endPointBody.createFixture(fixtureDef);
    //        shape.dispose();
    //        return endPointBody;
    //    }

    fun renderBackground() {
        tiledMapRenderer.setView(camera)
        tiledMapRenderer.renderObjects(layerBackgroundZero)
        tiledMapRenderer.renderObjects(layerBackgroundFirst)
        tiledMapRenderer.renderObjects(layerBackgroundSecond)
    }

    fun renderForeground() {
        tiledMapRenderer.renderObjects(layerForeground)
    }

    companion object {

        var worldWidth = 0f
        var worldHeight = 0f
    }

}