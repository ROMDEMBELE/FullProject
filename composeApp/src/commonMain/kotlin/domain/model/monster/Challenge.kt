package domain.model.monster

enum class Challenge(val rating: Double) {
    CR_0(0.0), // Dark Green
    CR_1_8(0.125), // Forest Green
    CR_1_4(0.25), // Yellow Green
    CR_1_2(0.5), // Goldenrod
    CR_1(1.0), // Dark Goldenrod
    CR_2(2.0), // Chocolate
    CR_3(3.0), // Saddle Brown
    CR_4(4.0), // Sienna
    CR_5(5.0), // Dark Red
    CR_6(6.0), // Firebrick
    CR_7(7.0), // Maroon
    CR_8(8.0), // Brown
    CR_9(9.0), // Brown
    CR_10(10.0), // Indigo
    CR_11(11.0), // Dark Slate Blue
    CR_12(12.0), // Slate Blue
    CR_13(13.0), // Medium Slate Blue
    CR_14(14.0), // Purple
    CR_15(15.0), // Blue Violet
    CR_16(16.0), // Purple
    CR_17(17.0), // Dark Purple
    CR_18(18.0), // Indigo
    CR_19(19.0), // Very Dark Purple
    CR_20(20.0), // Black
    CR_21(21.0), // Black
    CR_22(22.0), // Black
    CR_23(23.0), // Black
    CR_24(24.0), // Black
    CR_25(25.0), // Black
    CR_26(26.0), // Black
    CR_27(27.0), // Black
    CR_28(28.0), // Black
    CR_29(29.0), // Black
    CR_30(30.0); // Black

    companion object {
        fun fromRating(rating: Double): Challenge {
            return entries.find { it.rating == rating }
                ?: throw IllegalArgumentException("No Challenge found for rating $rating")
        }
    }
}