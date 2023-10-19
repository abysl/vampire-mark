package com.abysl.vampiremark.settings

data class GameSettings(
    val renderSettings: RenderSettings = RenderSettings.default_8,
    val physicsSettings: PhysicsSettings = PhysicsSettings()
)
