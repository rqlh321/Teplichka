package com.mygdx.game

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.Manifold

class MyContactListener : com.badlogic.gdx.physics.box2d.ContactListener {

    override fun beginContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body
        val objectA = bodyA.userData as Behavior
        val objectB = bodyB.userData as Behavior

    }

    override fun endContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body
        val objectA = bodyA.userData as Behavior
        val objectB = bodyB.userData as Behavior
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {

    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {

    }
}
