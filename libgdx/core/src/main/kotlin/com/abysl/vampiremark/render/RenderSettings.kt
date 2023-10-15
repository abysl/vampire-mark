import com.abysl.vampiremark.world.spatial.conversions.pixel
import com.abysl.vampiremark.world.spatial.conversions.tile
import com.abysl.vampiremark.world.spatial.coordinates.PixelPoint

data class RenderSettings(
    val baseResolution: PixelPoint,
    val viewportResolution: PixelPoint,
) {
    companion object {
        val default_8 = RenderSettings(PixelPoint(160.pixel, 90.pixel), PixelPoint(20.tile, 11.25.tile))
        val default_16 = RenderSettings(PixelPoint(320.pixel, 180.pixel), PixelPoint(20.tile, 11.25.tile))
        val default_32 = RenderSettings(PixelPoint(640.pixel, 360.pixel), PixelPoint(20.tile, 11.25.tile))
    }
}
