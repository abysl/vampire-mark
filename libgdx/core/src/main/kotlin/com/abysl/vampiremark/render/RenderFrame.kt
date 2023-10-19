package com.abysl.vampiremark.render

import com.badlogic.gdx.graphics.g2d.Sprite

data class RenderFrame(
    val drawables: List<Drawable>,
    val cameraPosition: CameraPosition
)
