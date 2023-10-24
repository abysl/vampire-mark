package com.abysl.vampiremark.screens

import com.abysl.vampiremark.ecs.entities.EntityFactory
import com.abysl.vampiremark.ecs.system.CameraSystem
import com.abysl.vampiremark.ecs.system.MovementSystem
import com.abysl.vampiremark.ecs.system.VelocitySystem
import com.abysl.vampiremark.ecs.components.PositionComponent
import com.abysl.vampiremark.ecs.components.TextureComponent
import com.abysl.vampiremark.ecs.components.VelocityComponent
import com.abysl.vampiremark.render.CameraPosition
import com.abysl.vampiremark.render.Drawable
import com.abysl.vampiremark.render.RenderFrame
import com.artemis.Aspect
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class ArtemisScreen : BaseScreen() {

    private val world: World = setupArtemisWorld()
    private val entityFactory = EntityFactory(world)
    private val texture = Texture(Gdx.files.internal("archer.png"))

    // Using the factory to create entities
    private val playerEntityId = entityFactory.createPlayer(texture)
    private val cameraId = entityFactory.createCamera()

    private val textureMapper = world.getMapper(TextureComponent::class.java)
    private val positionMapper = world.getMapper(PositionComponent::class.java)
    private val velocityMapper = world.getMapper(VelocityComponent::class.java)

    private fun setupArtemisWorld(): World {
        val config = WorldConfigurationBuilder()
            .with(VelocitySystem())
            .with(MovementSystem())
            .with(CameraSystem())
            .build()
        return World(config)
    }

    override fun update(tickRate: Float) {
        world.delta = tickRate
        world.process()
    }

    override fun dispose() {
        texture.dispose()
    }

    override fun getRenderFrame(): RenderFrame {
        val allEntityIds = world.aspectSubscriptionManager.get(
            Aspect.all(TextureComponent::class.java, PositionComponent::class.java)
        ).entities.data.toList()

        val cameraPosition = world.getEntity(cameraId).getComponent(PositionComponent::class.java).vec
        val cameraVelocity = world.getEntity(cameraId).getComponent(VelocityComponent::class.java).vec

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
