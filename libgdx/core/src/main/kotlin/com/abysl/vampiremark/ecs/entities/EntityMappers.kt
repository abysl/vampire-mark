package com.abysl.vampiremark.ecs.entities

import com.abysl.vampiremark.ecs.components.movement.PositionComponent
import com.abysl.vampiremark.ecs.components.TextureComponent
import com.abysl.vampiremark.ecs.components.movement.VelocityComponent
import com.artemis.World

class EntityMappers(val ecsWorld: World) {
    val position = ecsWorld.getMapper(PositionComponent::class.java)
    val velocity = ecsWorld.getMapper(VelocityComponent::class.java)
    val texture = ecsWorld.getMapper(TextureComponent::class.java)
}
