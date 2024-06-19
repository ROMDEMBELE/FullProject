package domain.model.character

import domain.model.Ability
import domain.model.Level

data class PlayerCharacter(
    val id: Long? = null,  // ID can be null if it's not assigned yet
    val fullName: String,
    val player: String,
    val level: Level,
    val armorClass: Int,
    val spellSavingThrow: Int,
    val hitPoint: Int,
    val scoreByAbilities: Map<Ability, Int>,
)