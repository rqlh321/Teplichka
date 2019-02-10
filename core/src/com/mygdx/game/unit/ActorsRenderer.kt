package com.mygdx.game.unit

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

class ActorsRenderer(vararg bodys: Body) {

    private val actors: List<Map<Vector2, Texture>> = listOf(mapOf(
            bodys[0].position to Texture(Gdx.files.internal("character/luchok/luchok.png")
            )))

    fun render(batch: Batch) {
        actors.forEach {
            it.entries.forEach { actor ->
                val texture = actor.value
                val position = actor.key
                batch.draw(texture, position.x - .8f, position.y - .8f, 1.6f, 1.6f)
            }
        }
    }
}