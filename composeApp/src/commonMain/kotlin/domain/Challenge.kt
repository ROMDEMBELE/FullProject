package domain

import androidx.compose.ui.graphics.Color
import kotlin.math.max
import kotlin.math.min

enum class Challenge(val rating: Double, val color: Color) {
    CR_0(0.0, Color(0xFF00FF00)), // Green
    CR_1_8(0.125, Color(0xFF32CD32)), // Lime Green
    CR_1_4(0.25, Color(0xFFADFF2F)), // Green Yellow
    CR_1_2(0.5, Color(0xFFFFFF00)), // Yellow
    CR_1(1.0, Color(0xFFFFD700)), // Gold
    CR_2(2.0, Color(0xFFFFA500)), // Orange
    CR_3(3.0, Color(0xFFFF8C00)), // Dark Orange
    CR_4(4.0, Color(0xFFFF4500)), // Orange Red
    CR_5(5.0, Color(0xFFFF0000)), // Red
    CR_6(6.0, Color(0xFFDC143C)), // Crimson
    CR_7(7.0, Color(0xFFB22222)), // Firebrick
    CR_8(8.0, Color(0xFF8B0000)), // Dark Red
    CR_9(9.0, Color(0xFFA52A2A)), // Brown
    CR_10(10.0, Color(0xFF800000)), // Maroon
    CR_11(11.0, Color(0xFF8B4513)), // Saddle Brown
    CR_12(12.0, Color(0xFF7B68EE)), // Medium Slate Blue
    CR_13(13.0, Color(0xFF6A5ACD)), // Slate Blue
    CR_14(14.0, Color(0xFF483D8B)), // Dark Slate Blue
    CR_15(15.0, Color(0xFF4B0082)), // Indigo
    CR_16(16.0, Color(0xFF9400D3)), // Dark Violet
    CR_17(17.0, Color(0xFF8A2BE2)), // Blue Violet
    CR_18(18.0, Color(0xFF6A0DAD)), // Purple
    CR_19(19.0, Color(0xFF4B0082)), // Indigo
    CR_20(20.0, Color(0xFF800080)), // Purple
    CR_21(21.0, Color(0xFF551A8B)), // Dark Purple
    CR_22(22.0, Color(0xFF2E0854)), // Very Dark Purple
    CR_23(23.0, Color(0xFF000000)), // Black
    CR_24(24.0, Color(0xFF000000)), // Black
    CR_30(30.0, Color(0xFF000000)); // Black

    fun next(): Challenge {
        val index = Challenge.entries.indexOf(this)
        return entries[min(index + 1, Challenge.entries.lastIndex)]
    }

    fun previous(): Challenge {
        val index = Challenge.entries.indexOf(this)
        return entries[max(index - 1, 0)]
    }

    override fun toString(): String = "CR $rating"

    companion object {
        fun fromDouble(rating: Double): Challenge {
            return entries.find { it.rating == rating }
                ?: throw IllegalArgumentException("No challenge corresponding $rating")
        }
    }
}