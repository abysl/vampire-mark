package com.abysl.vampiremark.ecs

import com.abysl.vampiremark.ecs.entities.EntityFactory
import com.abysl.vampiremark.ecs.entities.EntityMappers
import com.abysl.vampiremark.ecs.system.CameraSystem
import com.abysl.vampiremark.ecs.system.MovementSystem
import com.abysl.vampiremark.ecs.system.VelocitySystem
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class GameWorld {
    val ecs: World = initWorld()
    val entityFactory: EntityFactory = EntityFactory(ecs)
    val mappers: EntityMappers = EntityMappers(ecs)

    private val playerTexture = Texture(Gdx.files.internal("fmt/monsters/archer_r1.png"))

    var currentCamera: Int = entityFactory.createCamera()

    init {
        initEntities()
    }

    fun update(tickRate: Float) {
        ecs.delta = tickRate
        ecs.process()
    }

    private fun initWorld(): World {
        val config = WorldConfigurationBuilder()
            .with(VelocitySystem())
            .with(MovementSystem())
            .with(CameraSystem())
            .build()
        return World(config)
    }

    fun initEntities(){
        entityFactory.createPlayer(playerTexture)
    }
}

