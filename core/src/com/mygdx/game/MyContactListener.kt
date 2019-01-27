package com.mygdx.game

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

class MyContactListener : ContactListener {

    override fun beginContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body

        val objectA = bodyA.userData as Behavior
        val objectB = bodyB.userData as Behavior

        if (objectA.unit == Unit.PLAYER && objectB.unit == Unit.ENEMY) {
            objectA.connectTo.add(bodyB)
        }

        if (objectB.unit == Unit.PLAYER && objectA.unit == Unit.ENEMY) {
            objectB.connectTo.add(bodyA)
        }

    }

    override fun endContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body
        val objectA = bodyA.userData as Behavior
        val objectB = bodyB.userData as Behavior

        objectA.onGround = false
        objectB.onGround = false
        objectA.extraForce = true
        objectB.extraForce = true
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body
        val objectA = bodyA.userData as Behavior
        val objectB = bodyB.userData as Behavior

        objectA.onGround = true
        objectB.onGround = true
        objectA.extraForce = false
        objectB.extraForce = false
    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {

    }

}
