package domain

import androidx.compose.ui.graphics.Color

enum class Level(val level: Int, val color: Color) {
    LEVEL_0(0, Color(0xFFFFFFFF)),   // White
    LEVEL_1(1, Color(0xFFFFF0F0)),   // Lightest Red
    LEVEL_2(2, Color(0xFFFFE1E1)),
    LEVEL_3(3, Color(0xFFFFD2D2)),
    LEVEL_4(4, Color(0xFFFFC3C3)),
    LEVEL_5(5, Color(0xFFFFB4B4)),
    LEVEL_6(6, Color(0xFFFFA5A5)),
    LEVEL_7(7, Color(0xFFFF9696)),
    LEVEL_8(8, Color(0xFFFF8787)),
    LEVEL_9(9, Color(0xFFFF7878)),   // Pastel Red
    LEVEL_10(10, Color(0xFFFFFFFF)),  // White (Middle point)
    LEVEL_11(11, Color(0xFFFFFFFF)),
    LEVEL_12(12, Color(0xFFFFFFFF)),
    LEVEL_13(13, Color(0xFFFFFFFF)),
    LEVEL_14(14, Color(0xFFFFFFFF)),
    LEVEL_15(15, Color(0xFFFFFFFF)),
    LEVEL_16(16, Color(0xFFFFFFFF)),
    LEVEL_17(17, Color(0xFFFFFFFF)),
    LEVEL_18(18, Color(0xFFFFFFFF)),
    LEVEL_19(19, Color(0xFFFFFFFF)),
    LEVEL_20(20, Color(0xFFFFFFFF));  // White

    companion object {
        fun valueOfOrNull(name: String): Level? = entries.find { it.name == name }

        fun fromInt(level: Int): Level = entries.find { it.level == level } ?: LEVEL_0
    }
}