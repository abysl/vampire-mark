package com.abysl.vampiremark.render

class FPSCounter {
    private var frameCount = 0
    private var lastTime = System.currentTimeMillis()
    private var showFPS = false

    fun update() {
        frameCount++
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastTime

        if (elapsedTime > 1000) {  // Update every second
            if (showFPS) {
                println("FPS: ${frameCount / (elapsedTime / 1000.0)}")
                println("Frame Time: ${elapsedTime / frameCount.toDouble()} ms")
            }
            frameCount = 0
            lastTime = currentTime
        }
    }

    fun toggleFPSDisplay() {
        showFPS = !showFPS
    }
}
