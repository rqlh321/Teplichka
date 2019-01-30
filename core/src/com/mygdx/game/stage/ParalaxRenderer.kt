package com.mygdx.game.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Constants
import com.mygdx.game.Constants.VIEW_PORT_HEIGHT
import com.mygdx.game.Constants.VIEW_PORT_WIDTH
import com.mygdx.game.Layers

class ParalaxRenderer(private val camera: OrthographicCamera, private val batch: Batch) {

    private val bg2 = Texture(Gdx.files.internal("map/second/background_2_0.png"))
    private val bg1 = Texture(Gdx.files.internal("map/second/background_1_1.png"))
    private val bg0 = Texture(Gdx.files.internal("map/second/background_0.png"))

    private val backgrounds: List<Layer> = listOf(
            Layer(bg0, Vector2(0f, 0f), 0f, 0f),

            Layer(bg1, Vector2(3f, 0f), .1f, .04f),
            Layer(bg1, Vector2(6f, 0f), .1f, .04f),
            Layer(bg1, Vector2(9f, 0f), .1f, .04f),
            Layer(bg1, Vector2(12f, 0f), .1f, .04f),

            Layer(bg2, Vector2(0f, 0f), .3f, .06f),
            Layer(bg2, Vector2(1f, 0f), .3f, .06f),
            Layer(bg2, Vector2(2f, 0f), .3f, .06f),
            Layer(bg2, Vector2(3f, 0f), .3f, .06f),
            Layer(bg2, Vector2(4f, 0f), .3f, .06f),
            Layer(bg2, Vector2(5f, 0f), .3f, .06f),
            Layer(bg2, Vector2(6f, 0f), .3f, .06f),
            Layer(bg2, Vector2(7f, 0f), .3f, .06f),
            Layer(bg2, Vector2(8f, 0f), .3f, .06f),
            Layer(bg2, Vector2(9f, 0f), .3f, .06f),
            Layer(bg2, Vector2(10f, 0f), .3f, .06f),
            Layer(bg2, Vector2(11f, 0f), .3f, .06f),
            Layer(bg2, Vector2(12f, 0f), .3f, .06f),
            Layer(bg2, Vector2(13f, 0f), .3f, .06f),
            Layer(bg2, Vector2(14f, 0f), .3f, .06f),
            Layer(bg2, Vector2(15f, 0f), .3f, .06f),
            Layer(bg2, Vector2(16f, 0f), .3f, .06f)
    )

    private val foregroud: List<Layer> = emptyList()

    fun render() {
        backgrounds.forEach {
            batch.draw(it.texture, it.x(camera), it.y(camera), it.width, it.height)
        }
    }

    data class Layer(
            val texture: Texture,
            private val position: Vector2,
            private val xFactor: Float,
            private val yFactor: Float
    ) {
        val width = if (xFactor == 0f) VIEW_PORT_WIDTH else texture.width * Constants.SCALE * Constants.LEVEL_SCALE
        val height = if (yFactor == 0f) VIEW_PORT_HEIGHT else texture.height * Constants.SCALE * Constants.LEVEL_SCALE

        fun x(camera: OrthographicCamera) = camera.position.x - VIEW_PORT_WIDTH * camera.zoom / 2 + position.x - camera.position.x * xFactor
        fun y(camera: OrthographicCamera) = camera.position.y - VIEW_PORT_HEIGHT * camera.zoom / 2 + position.y - camera.position.y * yFactor
    }
}