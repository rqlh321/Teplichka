package com.mygdx.game

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.utils.Array

fun World.clear() {
    val bodyArray = Array<Body>()
    getBodies(bodyArray)
    bodyArray.forEach { bodyA ->
        val behavior = bodyA.userData as Behavior
        if (!behavior.alive) {
            destroyBody(bodyA)
        } else {
            behavior.connectTo.forEach { bodyB ->
                if (bodyB.jointList.size == 0) {
                    val jointDef = MotorJointDef()
                    jointDef.angularOffset = 0f
                    jointDef.collideConnected = false
                    jointDef.correctionFactor = 0f
                    jointDef.maxForce = 1f
                    jointDef.maxTorque = 1f
                    jointDef.initialize(bodyA, bodyB)
                    createJoint(jointDef)
                }
            }
            behavior.connectTo.clear()
        }
    }
}

fun World.initPlatform(): Body {
    val bodyDef = BodyDef()
    bodyDef.type = BodyDef.BodyType.StaticBody
    bodyDef.position.set(Vector2(0f, 0f))
    bodyDef.fixedRotation = false
    val body = createBody(bodyDef)
    body.userData = Behavior(Unit.PLATFORM)
    val bodyShape = ChainShape()
    val chain = FloatArray(10)
    chain.forEachIndexed { index, _ ->
        chain[index] = if (index % 2 == 0) index * 5f else Math.random().toFloat()
    }
    bodyShape.createChain(chain)
    val fixtureDefBody = FixtureDef()
    fixtureDefBody.shape = bodyShape
    body.createFixture(fixtureDefBody)
    bodyShape.dispose()
    return body
}

fun World.initBody(unit: Unit, position: Vector2): Body {
    val bodyDef = BodyDef()
    bodyDef.type = BodyDef.BodyType.DynamicBody
    bodyDef.position.set(position)

    bodyDef.fixedRotation = false
    val body = createBody(bodyDef)
    body.userData = Behavior(unit)

    val circleShape = CircleShape()
    val fixtureDefFoots = FixtureDef()

    when (unit) {
        Unit.ENEMY -> {
            circleShape.radius = Math.random().toFloat() * .1f
            fixtureDefFoots.restitution = Math.random().toFloat() * .1f
            fixtureDefFoots.density = Math.random().toFloat() * .1f
        }
        Unit.PLAYER -> {
            circleShape.radius = .1f
            fixtureDefFoots.restitution = .01f
            fixtureDefFoots.density = .01f
            fixtureDefFoots.friction = .01f

        }
    }

    fixtureDefFoots.shape = circleShape
    body.createFixture(fixtureDefFoots)
    circleShape.dispose()

    return body
}

fun Camera.smoothScrollOn(body: Body) {
    val dx = body.position.x - position.x
    val cameraX = position.x + dx * .2f

    val dy = body.position.y - position.y
    val cameraY = position.y + dy * .2f

    position.set(cameraX, cameraY, 0f)

    //        val velocity = player.linearVelocity.y
//        val dz = 1 + Math.abs(velocity) - camera.zoom
//        val coefficient = if (velocity >= 0) .05f else .01f
//        val cameraZ = camera.zoom + dz * coefficient
//        camera.zoom = cameraZ

    update()
}