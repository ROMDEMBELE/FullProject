package domain.model

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
enum class Level(val level: Int, val color: Color, val skillBonus: Int) {
    LEVEL_0(0, Color(0xFFFFFFFF), 2),   // White
    LEVEL_1(1, Color(0xFFFFF0F0), 2),   // Lightest Red
    LEVEL_2(2, Color(0xFFFFE1E1), 2),
    LEVEL_3(3, Color(0xFFFFD2D2), 2),
    LEVEL_4(4, Color(0xFFFFC3C3), 2),
    LEVEL_5(5, Color(0xFFFFB4B4), 3),
    LEVEL_6(6, Color(0xFFFFA5A5), 3),
    LEVEL_7(7, Color(0xFFFF9696), 3),
    LEVEL_8(8, Color(0xFFFF8787), 3),
    LEVEL_9(9, Color(0xFFFF7878), 4),   // Pastel Red
    LEVEL_10(10, Color(0xFFFF7878), 4),
    LEVEL_11(11, Color(0xFFFF7878), 4),
    LEVEL_12(12, Color(0xFFFF7878), 4),
    LEVEL_13(13, Color(0xFFFF7878), 5),
    LEVEL_14(14, Color(0xFFFF7878), 5),
    LEVEL_15(15, Color(0xFFFF7878), 5),
    LEVEL_16(16, Color(0xFFFF7878), 5),
    LEVEL_17(17, Color(0xFFFF7878), 6),
    LEVEL_18(18, Color(0xFFFF7878), 6),
    LEVEL_19(19, Color(0xFFFF7878), 6),
    LEVEL_20(20, Color(0xFFFF7878), 6);  // Pastel Red

    companion object {
        fun fromInt(level: Int): Level = entries.find { it.level == level }
            ?: throw IllegalArgumentException("Illegal $level do not correspond any Level")
    }
}