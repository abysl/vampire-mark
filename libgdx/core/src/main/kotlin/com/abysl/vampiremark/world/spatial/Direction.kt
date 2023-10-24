package com.abysl.vampiremark.world.spatial

import com.badlogic.gdx.math.Vector2

enum class Direction(val vec: Vector2) {
    NORTH(Vector2(0f, 1f)),
    NORTHEAST(Vector2(1f, 1f)),
    EAST(Vector2(1f, 0f)),
    SOUTHEAST(Vector2(1f, -1f)),
    SOUTH(Vector2(0f, -1f)),
    SOUTHWEST(Vector2(-1f, -1f)),
    WEST(Vector2(-1f, 0f)),
    NORTHWEST(Vector2(-1f, 1f))
}
