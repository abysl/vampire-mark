package com.abysl.vampiremark.screens

import com.abysl.vampiremark.ecs.GameWorld
import com.abysl.vampiremark.ecs.system.CameraSystem
import com.abysl.vampiremark.ecs.system.MovementSystem
import com.abysl.vampiremark.ecs.system.VelocitySystem
import com.abysl.vampiremark.render.*
import com.abysl.vampiremark.settings.GameSettings
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import ktx.app.KtxScreen

class GameScreen : KtxScreen {
    val settings: MutableStateFlow<GameSettings> = MutableStateFlow(GameSettings())

    protected var physicsDelta = 0f
    var currentRenderFrame: RenderFrame? = null

    private val world = GameWorld()
    private val ecsFrameAdapter = EcsFrameAdapter(world)
    private val renderer = GameRenderer(settings)


    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        renderer.resize(width, height)
    }

    override fun render(delta: Float) {
        physicsDelta += delta
        val tickRate = settings.value.physicsSettings.tickRate
        while (physicsDelta >= tickRate) {
            world.update(tickRate)
            currentRenderFrame = ecsFrameAdapter.getFrame()
            physicsDelta -= tickRate
        }

        currentRenderFrame?.let {
            renderer.render(it, physicsDelta)
        }
    }

    override fun dispose() {
        renderer.dispose()
    }
}
