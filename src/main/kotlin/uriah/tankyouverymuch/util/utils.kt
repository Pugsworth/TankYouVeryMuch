package uriah.tankyouverymuch.util

import net.minecraft.util.math.ColorHelper
import kotlin.math.min

object Utils {

    /**
     * Returns a color from green to yellow and red based on the fraction given.
     */
    fun getFractionColor(fract: Float): Int {
        val fi = 1.0f - fract

        val r = min(255.0f, fi*2 * 255.0f).toInt()
        val g = min(255.0f, fract*2 * 255.0f).toInt()

        val color = ColorHelper.Argb.getArgb(255, r, g, 0)

        return color
    }
}