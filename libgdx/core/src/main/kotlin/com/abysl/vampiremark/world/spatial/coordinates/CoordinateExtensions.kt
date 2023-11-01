package com.abysl.vampiremark.world.spatial.coordinates

import com.abysl.vampiremark.world.spatial.units.UnitExtensions.layer
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.pixel
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

fun Vector2.toPixelCoordinate() = PixelCoordinate(x.pixel, y.pixel, 0.layer)
fun Vector3.toPixelCoordinate() = PixelCoordinate(x.pixel, y.pixel, z.layer)
