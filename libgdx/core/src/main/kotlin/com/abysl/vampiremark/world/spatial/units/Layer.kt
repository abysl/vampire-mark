package com.abysl.vampiremark.world.spatial.units

data class Layer(val value: Int){
    companion object {
        val origin = Layer(0)
        val zero = origin;
    }
}
