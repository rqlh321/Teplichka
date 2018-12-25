package com.mygdx.game

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array

object Util {
    private val bodeArray = Array<Body>()

    fun clear(world: World) {
        world.getBodies(bodeArray)
        for (body in bodeArray) {
            val behavior = body.userData as Behavior
            if (!behavior.alive) world.destroyBody(body)
        }
        bodeArray.clear()
    }

    fun initBody(world: World, unit: Unit, position: Vector2): Body {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(position)

        bodyDef.fixedRotation = false
        val body = world.createBody(bodyDef)
        body.userData = Behavior(unit)

        val circleShape = CircleShape()
        val fixtureDefFoots = FixtureDef()

        when (unit) {
            Unit.ENEMY -> {
                circleShape.radius = Math.random().toFloat()*.1f
                fixtureDefFoots.restitution = Math.random().toFloat()*.1f
                fixtureDefFoots.density = Math.random().toFloat()*.1f
            }
            Unit.PLAYER -> {
                circleShape.radius = .03f
                fixtureDefFoots.restitution = .1f
                fixtureDefFoots.density = .5f
                fixtureDefFoots.friction=.1f
            }
        }

        fixtureDefFoots.shape = circleShape
        body.createFixture(fixtureDefFoots)
        circleShape.dispose()

        return body
    }

    fun initPlatform(world: World): Body {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position.set(Vector2(0f, 0f))
        bodyDef.fixedRotation = true
        val body = world.createBody(bodyDef)
        body.userData = Behavior(Unit.PLATFORM)
        val bodyShape = ChainShape()
        bodyShape.createChain(floatArrayOf(
                0F, 0F ,
                5F, 0F ,
                5F, 5F ,
                0F, 5F ,
                0F, 0F
        ))
        val fixtureDefBody = FixtureDef()
        fixtureDefBody.shape = bodyShape
        body.createFixture(fixtureDefBody)
        bodyShape.dispose()
        return body
    }

}
