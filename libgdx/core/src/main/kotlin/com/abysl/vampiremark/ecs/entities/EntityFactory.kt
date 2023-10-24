package com.abysl.vampiremark.ecs.entities

import com.abysl.vampiremark.ecs.components.*
import com.artemis.World
import com.badlogic.gdx.graphics.Texture

class EntityFactory(private val world: World) {

    private val textureMapper = world.getMapper(TextureComponent::class.java)
    private val positionMapper = world.getMapper(PositionComponent::class.java)
    private val velocityMapper = world.getMapper(VelocityComponent::class.java)

    fun createPlayer(texture: Texture): Int {
        val player = world.createEntity()
        world.edit(player.id)
            .add(PositionComponent())
            .add(VelocityComponent())
            .add(TextureComponent().set(texture))
            .add(LocalPlayerComponent())
        return player.id
    }

    fun createCamera(): Int {
        val camera = world.createEntity()
        world.edit(camera.id)
            .add(CameraComponent())
            .add(PositionComponent())
            .add(VelocityComponent())
        return camera.id
    }

    // Other entity creation methods can go here...
}
