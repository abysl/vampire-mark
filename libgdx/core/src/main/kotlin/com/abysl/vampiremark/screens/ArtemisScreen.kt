package com.abysl.vampiremark.screens

import com.abysl.vampiremark.ecs.artemis.component.ArtemisPositionComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisTextureComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisVelocityComponent
import com.abysl.vampiremark.ecs.artemis.system.ArtemisMovementSystem
import com.abysl.vampiremark.render.Drawable
import com.abysl.vampiremark.render.RenderFrame
import com.artemis.Aspect
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class ArtemisScreen : BaseScreen() {

    private lateinit var world: World
    private val texture = Texture(Gdx.files.internal("archer.png"))
    private val playerEntityId: Int

    init {
        // Set up Artemis world configuration
        val config = WorldConfigurationBuilder()
            .with(ArtemisMovementSystem())  // Add the movement system here
            .build()

        // Create Artemis world
        world = World(config)

        // Create player entity
        val player = world.createEntity()
        playerEntityId = player.id
        val playerPosition = ArtemisPositionComponent()
        val playerVelocity = ArtemisVelocityComponent()
        val playerSprite = ArtemisTextureComponent().initialize(texture)
        world.edit(playerEntityId).add(playerPosition).add(playerVelocity).add(playerSprite)
    }

    private var accumulator = 0f
    private val fixedDeltaTime = 1 / 60f  // 60 updates per second

    override fun render(delta: Float) {
        super.render(delta)
        accumulator += delta
        while (accumulator >= fixedDeltaTime) {
            world.delta = fixedDeltaTime
            world.process()
            accumulator -= fixedDeltaTime
        }
    }

    override fun dispose() {
        texture.dispose()
    }

    override fun getRenderFrame(): RenderFrame {
        val drawables = mutableListOf<Drawable>()

        val textureMapper = world.getMapper(ArtemisTextureComponent::class.java)
        val positionMapper = world.getMapper(ArtemisPositionComponent::class.java)

        val allEntityIds = world.aspectSubscriptionManager.get(Aspect.all(ArtemisTextureComponent::class.java, ArtemisPositionComponent::class.java)).entities.data.toList()

        for (entityId in allEntityIds) {
            val textureComponent = textureMapper.get(entityId)
            val positionComponent = positionMapper.get(entityId)
            val drawable = Drawable(
                texture = textureComponent.texture,
                position = positionComponent.vec
            )
            drawables.add(drawable)
        }

        return RenderFrame(drawables)
    }
}
