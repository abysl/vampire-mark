package com.abysl.vampiremark.screens

import com.abysl.vampiremark.render.FPSCounter
import com.abysl.vampiremark.settings.RenderSettings
import com.abysl.vampiremark.render.GameRenderer
import com.abysl.vampiremark.render.RenderFrame
import com.abysl.vampiremark.settings.GameSettings
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import ktx.app.KtxScreen

abstract class BaseScreen : KtxScreen {
    val settings: MutableStateFlow<GameSettings> = MutableStateFlow(GameSettings())
    protected val renderer = GameRenderer(settings)

    protected var physicsDelta = 0f
    var currentRenderFrame: RenderFrame? = null

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        renderer.resize(width, height)
    }

    override fun render(delta: Float) {
        physicsDelta += delta
        val tickRate = settings.value.physicsSettings.tickRate
        while (physicsDelta >= tickRate) {
            update(tickRate)
            currentRenderFrame = getRenderFrame()
            physicsDelta -= tickRate
        }

        currentRenderFrame?.let {
            renderer.render(it, physicsDelta)
        }
    }

    override fun dispose() {
        renderer.dispose()
    }

    abstract fun update(tickRate: Float)
    abstract fun getRenderFrame(): RenderFrame
}
