package uriah.tankyouverymuch.util

/**
 * A helper class for Minecraft to convert between ticks and duration.
 * @param value the value to convert.
 */
class TicksHelper(var value: Double)
{
    companion object {
        const val TICK_RATE = 20

        fun of(value: Double): TicksHelper {
            return TicksHelper(value)
        }

        fun of(value: Int): TicksHelper {
            return TicksHelper(value.toDouble())
        }
    }

    fun ticks(): Int {
        return value.toInt()
    }

    fun seconds(): Int {
        return (value * TICK_RATE.toDouble()).toInt()
    }

    fun second() = seconds()

    fun minutes(): Int {
        return seconds() * 60
    }

    fun minute() = minutes()

    fun hours(): Int {
        return minutes() * 60
    }

    fun hour() = hours()
}