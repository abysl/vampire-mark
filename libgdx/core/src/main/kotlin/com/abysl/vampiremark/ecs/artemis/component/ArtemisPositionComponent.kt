package com.abysl.vampiremark.ecs.artemis.component

import com.abysl.vampiremark.world.spatial.coordinates.PixelPoint
import com.artemis.Component

data class ArtemisPositionComponent(
    var position: PixelPoint = PixelPoint.origin
): Component()
