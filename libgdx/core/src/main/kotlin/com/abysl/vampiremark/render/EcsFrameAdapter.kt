package com.abysl.vampiremark.render

import com.abysl.vampiremark.world.GameWorld
import com.abysl.vampiremark.ecs.components.movement.PositionComponent
import com.abysl.vampiremark.ecs.components.TextureComponent
import com.abysl.vampiremark.world.tiles.ImmutableTileMap
import com.artemis.Aspect

class EcsFrameAdapter(private val world: GameWorld) {

    fun getFrame(): RenderFrame {
        return RenderFrame(getSprites(), getCameraVectors(), world.tilemap)
    }

    private fun getCameraVectors(): CameraPosition {
        val cameraPosition = world.mappers.position.get(world.currentCamera).vec
        val cameraVelocity = world.mappers.velocity.get(world.currentCamera).vec
        return CameraPosition(cameraPosition, cameraVelocity)
    }

    private fun getSprites(): List<Drawable> {
        val drawableEntities = world.ecs.aspectSubscriptionManager.get(
            Aspect.all(TextureComponent::class.java, PositionComponent::class.java)
        ).entities.data.filter { it != 0 }


        val sprites: List<Drawable> = drawableEntities.map { entityId ->
            val texture = world.mappers.texture.get(entityId).texture
            val position = world.mappers.position.get(entityId)
            val layer = position.z
            val velocity = world.mappers.velocity.get(entityId)?.vec
            Drawable(texture, position.vec, layer, velocity)
        }
        return sprites
    }
}
