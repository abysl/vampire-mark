package com.abysl.vampiremark.screens

import com.abysl.vampiremark.ecs.artemis.component.*
import com.abysl.vampiremark.ecs.artemis.system.ArtemisCameraSystem
import com.abysl.vampiremark.ecs.artemis.system.ArtemisMovementSystem
import com.abysl.vampiremark.ecs.artemis.system.ArtemisVelocitySystem
import com.abysl.vampiremark.render.CameraPosition
import com.abysl.vampiremark.render.Drawable
import com.abysl.vampiremark.render.RenderFrame
import com.artemis.Aspect
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class ArtemisScreen : BaseScreen() {

    private val world: World
    private val texture = Texture(Gdx.files.internal("archer.png"))
    private val playerEntityId: Int
    private val cameraId: Int

    init {
        world = setupArtemisWorld()
        playerEntityId = createPlayerEntity()
        cameraId = createCameraEntity()
    }

    private fun setupArtemisWorld(): World {
        val config = WorldConfigurationBuilder()
            .with(ArtemisVelocitySystem())
            .with(ArtemisMovementSystem())
            .with(ArtemisCameraSystem())
            .build()
        return World(config)
    }

    private fun createPlayerEntity(): Int {
        val player = world.createEntity()
        world.edit(player.id)
            .add(ArtemisPositionComponent())
            .add(ArtemisVelocityComponent())
            .add(ArtemisTextureComponent().set(texture))
            .add(ArtemisLocalPlayer())
        return player.id
    }

    private fun createCameraEntity(): Int {
        val camera = world.createEntity()
        world.edit(camera.id)
            .add(ArtemisCameraComponent())
            .add(ArtemisPositionComponent())
            .add(ArtemisVelocityComponent())
        return camera.id
    }
    override fun update(tickRate: Float) {
        world.delta = tickRate
        world.process()
    }

    override fun dispose() {
        texture.dispose()
    }

    override fun getRenderFrame(): RenderFrame {
        val textureMapper = world.getMapper(ArtemisTextureComponent::class.java)
        val positionMapper = world.getMapper(ArtemisPositionComponent::class.java)
        val velocityMapper = world.getMapper(ArtemisVelocityComponent::class.java)

        val allEntityIds = world.aspectSubscriptionManager.get(
            Aspect.all(ArtemisTextureComponent::class.java, ArtemisPositionComponent::class.java)
        ).entities.data.toList()
        val cameraPosition = world.getEntity(cameraId).getComponent(ArtemisPositionComponent::class.java).vec
        val cameraVelocity = world.getEntity(cameraId).getComponent(ArtemisVelocityComponent::class.java).vec

        val sprites = allEntityIds.map { entityId ->
            val textureComponent = textureMapper.get(entityId)
            val positionComponent = positionMapper.get(entityId)
            val velocityComponent = velocityMapper.get(entityId)

            Drawable(
                texture = textureComponent.texture,
                position = positionComponent.vec,
                layer = positionComponent.z,
                velocity = velocityComponent?.vec
            )
        }

        return RenderFrame(sprites, CameraPosition(cameraPosition, cameraVelocity))
    }

}
