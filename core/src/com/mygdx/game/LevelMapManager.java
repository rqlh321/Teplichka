package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.stage.MainStage;

/**
 * Created by sic on 20.01.2017.
 */

public class LevelMapManager {
    public TiledMap tiledMap;
    private ParallaxTiledMapRenderer tiledMapRenderer;
    private final MapLayer layerBackgroundZero;
    private final MapLayer layerBackgroundFirst;
    private final MapLayer layerBackgroundSecond;
    private final MapLayer layerForeground;
    private final MapLayer layerPlatformBody;
    private final MapLayer playerPositions;
    public final MapLayer enemiesLayer;

    private OrthographicCamera camera;
    private World world;

    public LevelMapManager(MainStage mainStage) {
        this.camera = (OrthographicCamera)mainStage.getViewport().getCamera();
        this.world = mainStage.getWorld();

        tiledMap = MyGdxGame.Companion.getASSET_MANAGER().get("map/second/map0.tmx");
        MapLayers mapLayers = tiledMap.getLayers();
        layerBackgroundZero = mapLayers.get("background_0");
        layerBackgroundFirst = mapLayers.get("background_1");
        layerBackgroundSecond = mapLayers.get("background_2");
        layerForeground = mapLayers.get("foreground");
        layerPlatformBody = mapLayers.get("platforms");
        playerPositions = mapLayers.get("player_positions");
        enemiesLayer = mapLayers.get("enemies");

        initPlatforms(world);

        tiledMapRenderer = new ParallaxTiledMapRenderer(tiledMap, camera);
    }

    private void initPlatforms(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body platform = world.createBody(bodyDef);
        platform.setUserData(new Behavior(Unit.PLATFORM));

        MapObjects worldObjects = layerPlatformBody.getObjects();
        for (MapObject objectWorld : worldObjects) {
            float[] vertices = ((PolylineMapObject) objectWorld).getPolyline().getTransformedVertices();
            Vector2[] worldVertices = new Vector2[vertices.length / 2];
            for (int j = 0; j < worldVertices.length; j++) {
                worldVertices[j] = new Vector2(vertices[j * 2]* Constants.INSTANCE.getSCALE() , vertices[j * 2 + 1]* Constants.INSTANCE.getSCALE());
            }
            ChainShape cs = new ChainShape();
            cs.createChain(worldVertices);
            platform.createFixture(cs, 1);
            cs.dispose();
        }
    }

    public Vector2 startPoint() {
        Vector2 playerPosition = new Vector2();
        MapObjects positions = playerPositions.getObjects();
        ((RectangleMapObject) positions.get("start_point"))
                .getRectangle()
                .getPosition(playerPosition);
        playerPosition.set(playerPosition.x * Constants.INSTANCE.getSCALE(), playerPosition.y * Constants.INSTANCE.getSCALE());
        return playerPosition;
    }

    public Body endPointBody() {
        Rectangle rectangle = ((RectangleMapObject) playerPositions.getObjects().get("end_point")).getRectangle();
        Vector2 endPoint = new Vector2();
        rectangle.getPosition(endPoint);
        endPoint.set(endPoint.x , endPoint.y );

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(endPoint.x + rectangle.getWidth() / 2 , endPoint.y + rectangle.getHeight() / 2 );

        Body endPointBody = world.createBody(bodyDef);
        endPointBody.setUserData("endPointBody");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangle.getWidth() / 2 , rectangle.getHeight() / 2 );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        endPointBody.createFixture(fixtureDef);
        shape.dispose();
        return endPointBody;
    }

    public void renderBackground() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.renderObjects(layerBackgroundZero);
        tiledMapRenderer.renderObjects(layerBackgroundFirst);
//        tiledMapRenderer.renderObjects(layerBackgroundSecond);
    }

    public void renderForeground() {
//        tiledMapRenderer.renderObjects(layerForeground);
    }
}