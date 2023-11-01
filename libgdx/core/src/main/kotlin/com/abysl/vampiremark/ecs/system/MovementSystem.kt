package com.abysl.vampiremark.ecs.system

import com.abysl.vampiremark.ecs.components.movement.PositionComponent
import com.abysl.vampiremark.ecs.components.movement.VelocityComponent
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import ktx.math.plusAssign
import ktx.math.times

@All(PositionComponent::class, VelocityComponent::class)
class MovementSystem : IteratingSystem() {

    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>
    @Wire
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    override fun initialize() {
        super.initialize()
        world.inject(this)  // Injects the mappers and any other dependencies
    }

    override fun process(entityId: Int) {
        val position = positionMapper[entityId]
        val velocity = velocityMapper[entityId]
        position.vec += (velocity.vec * world.delta)
    }
}
