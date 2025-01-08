package domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class Level(val level: Int) {
    LEVEL_0(0),   // White
    LEVEL_1(1),   // Lightest Red
    LEVEL_2(2),
    LEVEL_3(3),
    LEVEL_4(4),
    LEVEL_5(5),
    LEVEL_6(6),
    LEVEL_7(7),
    LEVEL_8(8),
    LEVEL_9(9),   // Pastel Red
    LEVEL_10(10),
    LEVEL_11(11),
    LEVEL_12(12),
    LEVEL_13(13),
    LEVEL_14(14),
    LEVEL_15(15),
    LEVEL_16(16),
    LEVEL_17(17),
    LEVEL_18(18),
    LEVEL_19(19),
    LEVEL_20(20);  // Pastel Red

    companion object {
        fun fromInt(level: Int): Level = entries.find { it.level == level }
            ?: throw IllegalArgumentException("Illegal $level do not correspond any Level")
    }
}