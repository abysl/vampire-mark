package com.abysl.vampiremark.screens

import com.abysl.vampiremark.ecs.artemis.component.ArtemisLocalPlayer
import com.abysl.vampiremark.ecs.artemis.component.ArtemisPositionComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisTextureComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisVelocityComponent
import com.abysl.vampiremark.ecs.artemis.system.ArtemisMovementSystem
import com.abysl.vampiremark.ecs.artemis.system.ArtemisVelocitySystem
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

    init {
        world = setupArtemisWorld()
        playerEntityId = createPlayerEntity()
    }

    private fun setupArtemisWorld(): World {
        val config = WorldConfigurationBuilder()
            .with(ArtemisVelocitySystem())
            .with(ArtemisMovementSystem())
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

        return RenderFrame(sprites)
    }

}
