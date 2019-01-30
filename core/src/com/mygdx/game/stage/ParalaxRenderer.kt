package com.mygdx.game.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

class ParalaxRenderer(private val camera: OrthographicCamera,private val  batch: Batch) {

    private val backgrounds: List<Map<Vector2, Texture>> = listOf(mapOf(
            Vector2(10f, 10f) to Texture(Gdx.files.internal("map/second/background_0.png")
            )))

    private val foregroud: List<Map<Vector2, Texture>> = emptyList()

    fun render() {
        val x = camera.position.x - camera.viewportWidth * camera.zoom / 2
        val y = camera.position.y - camera.viewportHeight * camera.zoom / 2

        backgrounds.forEach {
            it.entries.forEach { background ->
                val texture = background.value
                val position = background.key
                batch.draw(texture, x , y , texture.width*.0021f, 100f)
            }
        }
    }

}