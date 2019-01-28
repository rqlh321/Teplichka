package com.mygdx.game

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.mygdx.game.unit.Entity
import com.mygdx.game.unit.Type

class MyContactListener : ContactListener {

    override fun beginContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body

        val objectA = bodyA.userData as Entity
        val objectB = bodyB.userData as Entity

        if (objectA.type == Type.PLAYER && objectB.type == Type.ENEMY) {
        }

        if (objectB.type == Type.PLAYER && objectA.type == Type.ENEMY) {
        }

    }

    override fun endContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body
        val objectA = bodyA.userData as Entity
        val objectB = bodyB.userData as Entity

        objectA.onGround = false
        objectB.onGround = false
        objectA.extraForce = true
        objectB.extraForce = true
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body
        val objectA = bodyA.userData as Entity
        val objectB = bodyB.userData as Entity

        objectA.onGround = true
        objectB.onGround = true
        objectA.extraForce = false
        objectB.extraForce = false
    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {

    }

}
