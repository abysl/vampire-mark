package com.abysl.vampiremark.world.tiles

sealed class TileProperty

data class Speed(val multiplier: Double) : TileProperty()
data class Damage(val percentage: Double) : TileProperty()
data class Poison(val stacksPerSecond: Int) : TileProperty()
