package com.abysl.vampiremark.screens

import com.abysl.vampiremark.ecs.artemis.component.ArtemisPositionComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisSpriteComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisVelocityComponent
import com.abysl.vampiremark.ecs.artemis.system.ArtemisMovementSystem
import com.abysl.vampiremark.ecs.artemis.system.ArtemisRenderSystem
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
            .with(ArtemisRenderSystem(batch))  // Add the render system here
            .build()

        // Create Artemis world
        world = World(config)

        // Create player entity
        val player = world.createEntity()
        playerEntityId = player.id
        val playerPosition = ArtemisPositionComponent().apply { x = 100f; y = 100f }
        val playerVelocity = ArtemisVelocityComponent().apply { x = 0f; y = 0f }
        val playerSprite = ArtemisSpriteComponent().setTexture(texture)
        world.edit(playerEntityId).add(playerPosition).add(playerVelocity).add(playerSprite)
    }

    private var accumulator = 0f
    private val fixedDeltaTime = 1 / 60f  // 60 updates per second

    override fun render(delta: Float) {
//        accumulator += delta
//        while (accumulator >= fixedDeltaTime) {
            // Update the world's delta time and process the world
//            world.delta = fixedDeltaTime
            world.process()

//            accumulator -= fixedDeltaTime
//        }
    }

    override fun dispose() {
        texture.dispose()
    }
}
