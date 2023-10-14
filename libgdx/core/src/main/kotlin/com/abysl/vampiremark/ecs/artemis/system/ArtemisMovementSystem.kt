package com.abysl.vampiremark.ecs.artemis.system

import com.abysl.vampiremark.ecs.artemis.component.ArtemisPositionComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisVelocityComponent
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem

@All(ArtemisPositionComponent::class, ArtemisVelocityComponent::class)
class ArtemisMovementSystem : IteratingSystem() {

    @Wire
    private lateinit var positionMapper: ComponentMapper<ArtemisPositionComponent>
    @Wire
    private lateinit var velocityMapper: ComponentMapper<ArtemisVelocityComponent>

    override fun initialize() {
        super.initialize()
        world.inject(this)  // Injects the mappers and any other dependencies
    }

    override fun process(entityId: Int) {
        val position = positionMapper[entityId]
        val velocity = velocityMapper[entityId]

        position.x += velocity.x
        position.y += velocity.y
    }
}
