package com.mygdx.game.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Constants
import com.mygdx.game.Constants.VIEW_PORT_HEIGHT
import com.mygdx.game.Constants.VIEW_PORT_WIDTH
import kotlin.random.Random

class ParalaxRenderer(private val camera: OrthographicCamera, private val batch: Batch) {

    private val textures: List<Texture> = listOf(
            Texture(Gdx.files.internal("map/second/background_0.png")),
            Texture(Gdx.files.internal("map/second/background_1_1.png")),
            Texture(Gdx.files.internal("map/second/background_2_0.png"))
    )
    private val backgrounds: MutableList<Layer>

    init {
        backgrounds = ArrayList()
        textures.forEachIndexed { index, texture ->
            val factor: Float = index / 10f
            (0..(index * 10)).forEach {
                val x = if (index == 0) 0f else Random.nextInt(0, 30).toFloat()
                backgrounds.add(Layer(texture, Vector2(x, 0f), factor, factor))
            }
        }
    }

    private val foregroud: List<Layer> = emptyList()

    fun render() {
        backgrounds.forEach {
            batch.draw(it.texture, it.x(camera), it.y(camera), it.width(camera), it.height(camera))
        }
    }

    data class Layer(
            val texture: Texture,
            private val position: Vector2,
            private val xFactor: Float,
            private val yFactor: Float
    ) {
        fun width(camera: OrthographicCamera) = if (xFactor == 0f) VIEW_PORT_WIDTH * camera.zoom else texture.width * camera.zoom * Constants.MPP * Constants.LEVEL_SCALE
        fun height(camera: OrthographicCamera) = if (yFactor == 0f) VIEW_PORT_HEIGHT * camera.zoom else texture.height * camera.zoom * Constants.MPP * Constants.LEVEL_SCALE

        fun x(camera: OrthographicCamera) = camera.position.x - VIEW_PORT_WIDTH * camera.zoom / 2 + position.x - camera.position.x * xFactor
        fun y(camera: OrthographicCamera) = camera.position.y - VIEW_PORT_HEIGHT * camera.zoom / 2 + position.y - camera.position.y * yFactor
    }
}