package com.abysl.vampiremark.ecs.util

import com.abysl.vampiremark.ecs.Components
import com.abysl.vampiremark.world.tiles.TileProperty
import com.artemis.Aspect
import com.artemis.Component
import com.artemis.World

fun TileProperty.Filter.toAspect(world: World): Aspect {
    // Start with a basic Aspect.Builder instance
    var builder = Aspect.all()

    // Helper function to convert class name strings to Class instances
    fun classNamesToClasses(names: List<String>): List<Class<out Component>> {
        return names.map { className -> Components.fromString(className).java as Class<out Component> }
    }

    // Process the "ALL" conditions
    conditions[TileProperty.Filter.ConditionType.ALL]?.let {
        val classes = classNamesToClasses(it)
        builder = builder.all(classes)
    }

    // Process the "NONE" conditions
    conditions[TileProperty.Filter.ConditionType.NONE]?.let {
        val classes = classNamesToClasses(it)
        builder = builder.exclude(classes)
    }

    // Process the "ONE" conditions
    conditions[TileProperty.Filter.ConditionType.ONE]?.let {
        val classes = classNamesToClasses(it)
        builder = builder.one(classes)
    }

    // Assuming you have a reference to the World instance
    return builder.build(world)
}


