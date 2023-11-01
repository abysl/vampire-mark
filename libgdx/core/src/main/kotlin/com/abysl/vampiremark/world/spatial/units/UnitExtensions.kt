package com.abysl.vampiremark.world.spatial.units

object UnitExtensions {

    val Int.pixel: Pixel
        get() = Pixel(this)

    val Float.pixel: Pixel
        get() = Pixel(this.toInt())

    val Double.pixel: Pixel
        get() = Pixel(this.toInt())

    val Int.tile: Tile
        get() = Tile(this)

    val Float.tile: Tile
        get() = Tile(this.toInt())

    val Double.tile: Tile
        get() = Tile(this.toInt())

    val Int.chunk: Chunk
        get() = Chunk(this)

    val Float.chunk: Chunk
        get() = Chunk(this.toInt())

    val Double.chunk: Chunk
        get() = Chunk(this.toInt())

    val Int.layer: Layer
        get() = Layer(this)

    val Float.layer: Layer
        get() = Layer(this.toInt())

    val Double.layer: Layer
        get() = Layer(this.toInt())

}
