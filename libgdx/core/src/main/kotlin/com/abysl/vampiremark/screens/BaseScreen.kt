package com.abysl.vampiremark.screens

import RenderSettings
import com.abysl.vampiremark.render.GameRenderer
import com.abysl.vampiremark.render.RenderFrame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxScreen

abstract class BaseScreen : KtxScreen {

    val renderer = GameRenderer(RenderSettings.default_16)

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        renderer.resize(width, height)
    }

    override fun render(delta: Float) {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.render(getRenderFrame())
    }

    override fun dispose() {
        renderer.dispose()
    }

    abstract fun getRenderFrame(): RenderFrame
}
