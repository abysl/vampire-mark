package com.abysl.vampiremark

import com.abysl.vampiremark.screens.ArtemisScreen
import com.abysl.vampiremark.screens.DominionScreen
import com.abysl.vampiremark.screens.FleksScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen

class VampireMark : KtxGame<KtxScreen>() {

    override fun create() {
        addScreen(ArtemisScreen())
        addScreen(DominionScreen())
        addScreen(FleksScreen())
        // Set initial screen
        setScreen<ArtemisScreen>()
    }

    fun switchToArtemis() {
        setScreen<ArtemisScreen>()
    }

    fun switchToDominion() {
        setScreen<DominionScreen>()
    }

    fun switchToFleks() {
        setScreen<FleksScreen>()
    }
}
