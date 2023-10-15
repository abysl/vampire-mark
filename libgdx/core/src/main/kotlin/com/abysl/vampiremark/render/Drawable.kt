package com.abysl.vampiremark.render

import com.abysl.vampiremark.world.spatial.coordinates.PixelPoint
import com.badlogic.gdx.graphics.Texture

data class Drawable(
    val texture: Texture,
    val position: PixelPoint
)
