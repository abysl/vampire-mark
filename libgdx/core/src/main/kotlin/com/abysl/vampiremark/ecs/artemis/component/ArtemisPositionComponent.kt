package com.abysl.vampiremark.ecs.artemis.component

import com.artemis.Component

data class ArtemisPositionComponent(
    var x: Float = 0f,
    var y: Float = 0f
): Component()
