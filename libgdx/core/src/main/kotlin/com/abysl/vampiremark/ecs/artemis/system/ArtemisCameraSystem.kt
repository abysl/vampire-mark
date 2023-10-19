package com.abysl.vampiremark.ecs.artemis.system

import com.abysl.vampiremark.ecs.artemis.component.ArtemisCameraComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisLocalPlayer
import com.abysl.vampiremark.ecs.artemis.component.ArtemisPositionComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisVelocityComponent
import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper

class ArtemisCameraSystem : BaseEntitySystem(
    Aspect.all(ArtemisPositionComponent::class.java, ArtemisVelocityComponent::class.java, ArtemisLocalPlayer::class.java)
) {
    private lateinit var positionMapper: ComponentMapper<ArtemisPositionComponent>
    private lateinit var velocityMapper: ComponentMapper<ArtemisVelocityComponent>
    private lateinit var cameraMapper: ComponentMapper<ArtemisCameraComponent>

    override fun processSystem() {
        // Get the player entity with the player tag
        world.aspectSubscriptionManager.get(Aspect.all(ArtemisLocalPlayer::class.java)).entities.data.firstOrNull()?.let { player ->
            val playerPosition = positionMapper.get(player)
            val playerVelocity = velocityMapper.get(player)

            // Fetch the camera entity based on the ArtemisCameraComponent tag
            world.aspectSubscriptionManager.get(Aspect.all(ArtemisCameraComponent::class.java)).entities.data.firstOrNull()?.let { cameraEntity ->
                positionMapper.get(cameraEntity).set(playerPosition.vec)
                velocityMapper.get(cameraEntity).set(playerVelocity.vec)
            }
        }
    }
}

