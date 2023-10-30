package com.abysl.vampiremark.settings

import kotlinx.serialization.json.Json

data class GameSettings(
    val renderSettings: RenderSettings = RenderSettings.default_8,
    val physicsSettings: PhysicsSettings = PhysicsSettings(),
    val jsonSettings: Json = Json {
        ignoreUnknownKeys = true
    }
)
