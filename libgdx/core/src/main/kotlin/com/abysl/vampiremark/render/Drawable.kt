package com.abysl.vampiremark.render

import com.abysl.vampiremark.math.GameVector
import com.badlogic.gdx.graphics.Texture

data class Drawable(
    val texture: Texture,
    val position: GameVector
)
