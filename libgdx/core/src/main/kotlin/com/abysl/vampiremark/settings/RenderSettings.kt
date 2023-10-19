package com.abysl.vampiremark.settings

import com.abysl.vampiremark.world.spatial.conversions.tile
import com.badlogic.gdx.math.Vector2

data class RenderSettings(
    val baseResolution: Vector2,
) {
    companion object {
        val default_8 = RenderSettings(Vector2(160f, 90f))
        val default_16 = RenderSettings(Vector2(320f, 180f))
        val default_32 = RenderSettings(Vector2(640f, 360f))
    }
}
