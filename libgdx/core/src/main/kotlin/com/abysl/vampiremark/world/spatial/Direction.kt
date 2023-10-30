package com.abysl.vampiremark.world.spatial

import com.badlogic.gdx.math.Vector2
import kotlinx.serialization.Serializable


@Serializable
enum class Direction(val x: Int, val y: Int) {
    NORTH(0, 1),
    NORTHEAST(1,1),
    EAST(1,0),
    SOUTHEAST(1, -1),
    SOUTH(0, -1),
    SOUTHWEST(-1, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1);

    val vec: Vector2 by lazy { Vector2(this.x.toFloat(), this.y.toFloat())}
}
