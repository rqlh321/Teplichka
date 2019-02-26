package com.mygdx.game.movement

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.mygdx.game.Constants
import com.mygdx.game.unit.Entity
import com.mygdx.game.unit.Type

class MyContactListener : ContactListener {

    override fun beginContact(contact: Contact) {
        val fixtureAData = contact.fixtureA.userData as String
        val fixtureBData = contact.fixtureB.userData as String

        val bodyA = contact.fixtureA.body
        val entityA = bodyA.userData as Entity

        val bodyB = contact.fixtureB.body
        val entityB = bodyB.userData as Entity

        if (fixtureAData == Entity.FOOT) {
            entityA.onGround = true
            entityA.extraForce = false
        } else if (fixtureBData == Entity.FOOT) {
            entityB.onGround = true
            entityB.extraForce = false
        }

    }

    override fun endContact(contact: Contact) {
        val fixtureAData = contact.fixtureA.userData as String
        val fixtureBData = contact.fixtureB.userData as String

        val objectA = contact.fixtureA.body.userData as Entity
        val objectB = contact.fixtureB.body.userData as Entity

        if (fixtureAData == Entity.FOOT) {
            objectA.onGround = false
            objectA.extraForce = true
        } else if (fixtureBData == Entity.FOOT) {
            objectB.onGround = false
            objectB.extraForce = true
        }

    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) = Unit

    override fun postSolve(contact: Contact, impulse: ContactImpulse) = Unit

}