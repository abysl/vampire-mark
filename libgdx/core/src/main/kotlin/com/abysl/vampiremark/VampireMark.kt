package com.abysl.vampiremark

import com.abysl.vampiremark.screens.GameScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen

class VampireMark : KtxGame<KtxScreen>() {

    override fun create() {
        addScreen(GameScreen())
        // Set initial screen
        setScreen<GameScreen>()
    }
}
