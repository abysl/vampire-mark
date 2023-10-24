package com.abysl.vampiremark.ecs.system

import com.abysl.vampiremark.ecs.components.LocalPlayerComponent
import com.abysl.vampiremark.ecs.components.PositionComponent
import com.abysl.vampiremark.ecs.components.VelocityComponent
import com.abysl.vampiremark.world.spatial.conversions.tile
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

@All(PositionComponent::class, VelocityComponent::class, LocalPlayerComponent::class)
class VelocitySystem : BaseEntitySystem() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>
    @Wire
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    private val player_speed = 3.tile

    override fun processSystem() {
        for (localPlayer in subscription.entities.data) {
            val velocity = velocityMapper.get(localPlayer)

            // Reset velocity
            velocity.x = 0
            velocity.y = 0

            // Vertical movement
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                velocity.y = 1
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                velocity.y = -1
            }

            // Horizontal movement
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velocity.x = -1
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velocity.x = 1
            }

            // Normalize if there's movement
            if (velocity.x != 0 || velocity.y != 0) {
                velocity.vec.nor().scl(player_speed.toFloat())
            }
        }
    }
}

