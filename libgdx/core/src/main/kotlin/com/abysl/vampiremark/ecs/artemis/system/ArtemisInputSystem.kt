package com.abysl.vampiremark.ecs.artemis.system

import com.abysl.vampiremark.ecs.artemis.component.ArtemisPlayerTag
import com.abysl.vampiremark.ecs.artemis.component.ArtemisPositionComponent
import com.abysl.vampiremark.ecs.artemis.component.ArtemisVelocityComponent
import com.abysl.vampiremark.world.spatial.conversions.pixel
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.artemis.annotations.Wire
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

@All(ArtemisPositionComponent::class, ArtemisVelocityComponent::class, ArtemisPlayerTag::class)
class ArtemisInputSystem : BaseEntitySystem() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<ArtemisPositionComponent>
    @Wire
    private lateinit var velocityMapper: ComponentMapper<ArtemisVelocityComponent>

    override fun processSystem() {
        val deltaTime = world.delta

        val entities = subscription.entities
        for (i in 0 until entities.size()) {
            val entity = entities[i]

            val positionComponent = positionMapper.get(entity)
            val velocityComponent = velocityMapper.get(entity)

//            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                positionComponent.position += (velocityComponent.velocity.y.toFloat * deltaTime).toInt().pixel
//            }
//            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                positionComponent.y -= velocityComponent.y * deltaTime
//            }
//            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                positionComponent.x -= velocityComponent.x * deltaTime
//            }
//            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                positionComponent.x += velocityComponent.x * deltaTime
//            }
        }
    }
}

