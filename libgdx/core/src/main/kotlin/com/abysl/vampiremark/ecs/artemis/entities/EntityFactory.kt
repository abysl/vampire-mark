package com.abysl.vampiremark.ecs.artemis.entities

import com.abysl.vampiremark.ecs.artemis.component.*
import com.artemis.World
import com.badlogic.gdx.graphics.Texture

class EntityFactory(private val world: World) {

    private val textureMapper = world.getMapper(ArtemisTextureComponent::class.java)
    private val positionMapper = world.getMapper(ArtemisPositionComponent::class.java)
    private val velocityMapper = world.getMapper(ArtemisVelocityComponent::class.java)

    fun createPlayer(texture: Texture): Int {
        val player = world.createEntity()
        world.edit(player.id)
            .add(ArtemisPositionComponent())
            .add(ArtemisVelocityComponent())
            .add(ArtemisTextureComponent().set(texture))
            .add(ArtemisLocalPlayer())
        return player.id
    }

    fun createCamera(): Int {
        val camera = world.createEntity()
        world.edit(camera.id)
            .add(ArtemisCameraComponent())
            .add(ArtemisPositionComponent())
            .add(ArtemisVelocityComponent())
        return camera.id
    }

    // Other entity creation methods can go here...
}
