package com.abysl.vampiremark.ecs.artemis.system

import com.abysl.vampiremark.ecs.artemis.component.ArtemisPositionComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisSpriteComponent
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch

@All(ArtemisPositionComponent::class, ArtemisSpriteComponent::class)
class ArtemisRenderSystem(private val batch: SpriteBatch) : IteratingSystem() {

    @Wire private lateinit var positionMapper: ComponentMapper<ArtemisPositionComponent>
    @Wire private lateinit var spriteMapper: ComponentMapper<ArtemisSpriteComponent>

    override fun initialize() {
        super.initialize()
        world.inject(this)  // Injects the mappers and any other dependencies
    }

    override fun process(entityId: Int) {
        val position = positionMapper[entityId]
        val spriteComponent = spriteMapper[entityId]

        batch.begin()
        batch.draw(spriteComponent.texture, position.x, position.y)
        batch.end()
    }
}

