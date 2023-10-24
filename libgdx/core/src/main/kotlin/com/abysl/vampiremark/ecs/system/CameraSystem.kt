package com.abysl.vampiremark.ecs.system

import com.abysl.vampiremark.ecs.components.CameraComponent
import com.abysl.vampiremark.ecs.components.LocalPlayerComponent
import com.abysl.vampiremark.ecs.components.PositionComponent
import com.abysl.vampiremark.ecs.components.VelocityComponent
import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper

class CameraSystem : BaseEntitySystem(
    Aspect.all(PositionComponent::class.java, VelocityComponent::class.java, LocalPlayerComponent::class.java)
) {
    private lateinit var positionMapper: ComponentMapper<PositionComponent>
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>
    private lateinit var cameraMapper: ComponentMapper<CameraComponent>

    override fun processSystem() {
        // Get the player entity with the player tag
        world.aspectSubscriptionManager.get(Aspect.all(LocalPlayerComponent::class.java)).entities.data.firstOrNull()?.let { player ->
            val playerPosition = positionMapper.get(player)
            val playerVelocity = velocityMapper.get(player)

            // Fetch the camera entity based on the ArtemisCameraComponent tag
            world.aspectSubscriptionManager.get(Aspect.all(CameraComponent::class.java)).entities.data.firstOrNull()?.let { cameraEntity ->
                positionMapper.get(cameraEntity).set(playerPosition.vec)
                velocityMapper.get(cameraEntity).set(playerVelocity.vec)
            }
        }
    }
}

