package com.abysl.vampiremark

import com.abysl.vampiremark.screens.ArtemisScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen

class VampireMark : KtxGame<KtxScreen>() {

    override fun create() {
        addScreen(ArtemisScreen())
        // Set initial screen
        setScreen<ArtemisScreen>()
    }
}
