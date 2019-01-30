package com.mygdx.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer

class ParallaxTiledMapRenderer(map: TiledMap, private val camera: OrthographicCamera) : BatchTiledMapRenderer(map) {

    override fun renderObject(mapObject: MapObject?) {
        super.renderObject(mapObject)
        if (mapObject is TextureMapObject) {
            val texture = mapObject as TextureMapObject?
            batch.begin()
            if (mapObject.name != null && mapObject.name == "background_0") {

                val x = camera.position.x - camera.viewportWidth * camera.zoom / 2
                val y = camera.position.y - camera.viewportHeight * camera.zoom / 2
                batch.draw(
                        texture!!.textureRegion,
                        x,
                        y,
                        texture.originX,
                        texture.originY,
                        texture.textureRegion.regionWidth.toFloat(),
                        texture.textureRegion.regionHeight.toFloat(),
                        .0021f,
                        .00225f,
                        texture.rotation
                )
            } else if (mapObject.name != null && mapObject.name == "background_1") {
                val convertedPositionX = texture!!.x * Constants.SCALE * Constants.LEVEL_SCALE
                val convertedPositionY = texture.y * Constants.SCALE * Constants.LEVEL_SCALE
                batch.draw(
                        texture.textureRegion,
                        convertedPositionX + (camera.position.x - camera.viewportWidth / 2) - camera.position.x * .1f,
                        convertedPositionY + (camera.position.y - camera.viewportHeight / 2) - camera.position.y * .04f,
                        texture.originX,
                        texture.originY,
                        texture.textureRegion.regionWidth.toFloat(),
                        texture.textureRegion.regionHeight.toFloat(),
                        texture.scaleX * Constants.SCALE * Constants.LEVEL_SCALE,
                        texture.scaleY * Constants.SCALE * Constants.LEVEL_SCALE,
                        texture.rotation
                )
            } else if (mapObject.name != null && mapObject.name == "background_2") {
                val convertedPositionX = texture!!.x * Constants.SCALE * Constants.LEVEL_SCALE
                val convertedPositionY = texture.y * Constants.SCALE * Constants.LEVEL_SCALE
                batch.draw(
                        texture.textureRegion,
                        convertedPositionX + (camera.position.x - camera.viewportWidth / 2) - camera.position.x * .3f,
                        convertedPositionY + (camera.position.y - camera.viewportHeight / 2) - camera.position.y * .06f,
                        texture.originX,
                        texture.originY,
                        texture.textureRegion.regionWidth.toFloat(),
                        texture.textureRegion.regionHeight.toFloat(),
                        texture.scaleX * Constants.SCALE * Constants.LEVEL_SCALE,
                        texture.scaleY * Constants.SCALE * Constants.LEVEL_SCALE,
                        texture.rotation
                )
            } else {
                val convertedPositionX = texture!!.x * Constants.SCALE * Constants.LEVEL_SCALE
                val convertedPositionY = texture.y * Constants.SCALE * Constants.LEVEL_SCALE
                batch.draw(
                        texture.textureRegion,
                        texture.x,
                        texture.y,
                        texture.originX,
                        texture.originY,
                        texture.textureRegion.regionWidth.toFloat(),
                        texture.textureRegion.regionHeight.toFloat(),
                        texture.scaleX * Constants.SCALE * Constants.LEVEL_SCALE,
                        texture.scaleY * Constants.SCALE * Constants.LEVEL_SCALE,
                        texture.rotation
                )
            }
            batch.end()
        }
    }

    override fun renderTileLayer(layer: TiledMapTileLayer?) {
        if (layer != null)
            for (`object` in layer.objects) {
                renderObject(`object`)
            }
    }

}
