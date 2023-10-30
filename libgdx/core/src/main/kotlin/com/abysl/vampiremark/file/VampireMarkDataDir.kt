package com.abysl.vampiremark.file

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle

object VampireMarkDataDir {

    val rootDir: FileHandle by lazy {
        when {
            Gdx.app.type == Application.ApplicationType.Android -> Gdx.files.local("data/")
            Gdx.app.type == Application.ApplicationType.Desktop -> {
                val os = System.getProperty("os.name").lowercase()
                when {
                    "win" in os -> Gdx.files.absolute(System.getenv("APPDATA") + "/VampireMark/")
                    "mac" in os -> Gdx.files.absolute(System.getProperty("user.home") + "/Library/Application Support/VampireMark/")
                    else -> Gdx.files.absolute(System.getProperty("user.home") + "/.vampire-mark/")
                }
            }
            Gdx.app.type == Application.ApplicationType.iOS -> Gdx.files.local("data/")
            else -> Gdx.files.local("data/")  // Default to local storage for other platforms
        }.apply {
            if (!exists()) mkdirs()
        }
    }

    fun getPathFor(filename: String): FileHandle {
        return rootDir.child(filename)
    }
}
