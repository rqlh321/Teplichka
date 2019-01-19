package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;

public class ParallaxTiledMapRenderer extends BatchTiledMapRenderer {
    private OrthographicCamera camera;

    public ParallaxTiledMapRenderer(TiledMap map, OrthographicCamera camera) {
        super(map);
        this.camera = camera;
    }

    @Override
    public void renderObject(MapObject object) {
        super.renderObject(object);
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
            batch.begin();
            if (object.getName() != null && object.getName().equals("background_0")) {
                batch.draw(
                        textureObject.getTextureRegion(),
                        camera.position.x - camera.viewportWidth * camera.zoom / 2,
                        camera.position.y - camera.viewportHeight * camera.zoom / 2,
                        textureObject.getOriginX(),
                        textureObject.getOriginY(),
                        textureObject.getTextureRegion().getRegionWidth(),
                        textureObject.getTextureRegion().getRegionHeight(),
                        .0021f,
                        .00225f,
                        textureObject.getRotation()
                );
            } else if (object.getName() != null && object.getName().equals("background_1")) {
                float convertedPositionX = textureObject.getX() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE();
                float convertedPositionY = textureObject.getY() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE();
                batch.draw(
                        textureObject.getTextureRegion(),
                        convertedPositionX + (camera.position.x - (camera.viewportWidth / 2)) - camera.position.x * .1f,
                        convertedPositionY + (camera.position.y - (camera.viewportHeight / 2)) - camera.position.y * .04f,
                        textureObject.getOriginX(),
                        textureObject.getOriginY(),
                        textureObject.getTextureRegion().getRegionWidth(),
                        textureObject.getTextureRegion().getRegionHeight(),
                        textureObject.getScaleX() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE(),
                        textureObject.getScaleY() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE(),
                        textureObject.getRotation()
                );
            } else if (object.getName() != null && object.getName().equals("background_2")) {
                float convertedPositionX = textureObject.getX() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE();
                float convertedPositionY = textureObject.getY() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE();
                batch.draw(
                        textureObject.getTextureRegion(),
                        convertedPositionX + (camera.position.x - (camera.viewportWidth / 2)) - camera.position.x * .3f,
                        convertedPositionY + (camera.position.y - (camera.viewportHeight / 2)) - camera.position.y * .06f,
                        textureObject.getOriginX(),
                        textureObject.getOriginY(),
                        textureObject.getTextureRegion().getRegionWidth(),
                        textureObject.getTextureRegion().getRegionHeight(),
                        textureObject.getScaleX() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE(),
                        textureObject.getScaleY() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE(),
                        textureObject.getRotation()
                );
            } else {
                float convertedPositionX = textureObject.getX() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE();
                float convertedPositionY = textureObject.getY() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE();
                batch.draw(
                        textureObject.getTextureRegion(),
                        textureObject.getX(),
                        textureObject.getY(),
                        textureObject.getOriginX(),
                        textureObject.getOriginY(),
                        textureObject.getTextureRegion().getRegionWidth(),
                        textureObject.getTextureRegion().getRegionHeight(),
                        textureObject.getScaleX() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE(),
                        textureObject.getScaleY() * Constants.INSTANCE.getSCALE() * Constants.INSTANCE.getLEVEL_SCALE(),
                        textureObject.getRotation()
                );
            }
            batch.end();
        }
    }

    @Override
    public void renderTileLayer(TiledMapTileLayer layer) {
        if (layer != null)
            for (MapObject object : layer.getObjects()) {
                renderObject(object);
            }
    }

}
