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

        objectA.updateJump(objectB, true)
        objectB.updateJump(objectA, true)
    }

    override fun endContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body
        val objectA = bodyA.userData as Behavior
        val objectB = bodyB.userData as Behavior
        objectA.updateJump(objectB, false)
        objectB.updateJump(objectA, false)
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) = Unit

    override fun postSolve(contact: Contact, impulse: ContactImpulse) = Unit

}
