package com.abysl.vampiremark.render

import com.abysl.vampiremark.world.tiles.TileMap

data class RenderFrame(
    val drawables: List<Drawable>,
    val cameraPosition: CameraPosition,
    val tileMap: TileMap
)
