package com.abysl.vampiremark.world.spatial.coordinates

import com.abysl.vampiremark.world.spatial.conversions.layer
import com.abysl.vampiremark.world.spatial.conversions.pixel
import com.abysl.vampiremark.world.spatial.units.Layer
import com.abysl.vampiremark.world.spatial.units.Pixel

data class PixelPoint(
    val x: Pixel,
    val y: Pixel,
    val z: Layer = Layer.origin,
){

    val toChunkPoint by lazy { ChunkPoint(x.toChunk, y.toChunk) }

    fun layer(layer: Layer): PixelPoint = PixelPoint(this.x, this.y, layer)
    fun x(pixel: Pixel): PixelPoint = PixelPoint(pixel, this.y, this.z)
    fun y(pixel: Pixel): PixelPoint = PixelPoint(this.x, pixel, this.z)

    val area: Pixel by lazy { this.x * this.y }
    val abs: PixelPoint by lazy { PixelPoint(this.x.abs(), this.y.abs(), z) }

    operator fun plus(other: PixelPoint) = PixelPoint(this.x + other.x, this.y + other.y, this.z)
    operator fun minus(other: PixelPoint) = PixelPoint(this.x - other.x, this.y - other.y, this.z)
    operator fun times(other: PixelPoint) = PixelPoint(this.x * other.x, this.y * other.y, this.z)
    operator fun div(other: PixelPoint) = PixelPoint(this.x / other.x, this.y / other.y, this.z)

    operator fun plus(other: Int) = PixelPoint(this.x + other, this.y + other, this.z)
    operator fun minus(other: Int) = PixelPoint(this.x - other, this.y - other, this.z)
    operator fun times(other: Int) = PixelPoint(this.x * other, this.y * other, this.z)
    operator fun div(other: Int) = PixelPoint(this.x / other, this.y / other, this.z)

    companion object {
        val origin = PixelPoint(0.pixel, 0.pixel , 0.layer);
        val zero = origin;
        val one = PixelPoint(1.pixel, 1.pixel, 0.layer)
    }

}
