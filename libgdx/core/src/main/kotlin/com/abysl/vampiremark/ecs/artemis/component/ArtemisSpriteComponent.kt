package com.abysl.vampiremark.ecs.artemis.component

import com.artemis.Component
import com.badlogic.gdx.graphics.Texture

class ArtemisSpriteComponent : Component() {
    lateinit var texture: Texture

    fun setTexture(texture: Texture): ArtemisSpriteComponent {
        this.texture = texture
        return this
    }
}
