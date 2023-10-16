package com.abysl.vampiremark.ecs.artemis.system

import com.abysl.vampiremark.ecs.artemis.component.ArtemisLocalPlayer
import com.abysl.vampiremark.ecs.artemis.component.ArtemisPositionComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisVelocityComponent
import com.abysl.vampiremark.world.spatial.conversions.tile
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

@All(ArtemisPositionComponent::class, ArtemisVelocityComponent::class, ArtemisLocalPlayer::class)
class ArtemisVelocitySystem : BaseEntitySystem() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<ArtemisPositionComponent>
    @Wire
    private lateinit var velocityMapper: ComponentMapper<ArtemisVelocityComponent>

    private val player_speed = 2.tile

    override fun processSystem() {
        val deltaTime = world.delta
        val entities = subscription.entities
        for (localPlayer in subscription.entities.data) {
            val positionComponent = positionMapper.get(localPlayer)
            val velocityComponent = velocityMapper.get(localPlayer)


            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                velocityComponent.vec.y = 1
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                velocityComponent.vec.y = -1
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velocityComponent.vec.x = -1
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velocityComponent.vec.y = 1
            }
            velocityComponent.vec.normalize()
        }
    }
}

