package com.abysl.vampiremark.render

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

data class Drawable(
    val texture: Texture,
    val position: Vector2,
    val layer: Byte,
    val velocity: Vector2? = null
)
