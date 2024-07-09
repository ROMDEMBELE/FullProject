package data.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EncounterWithFightersAndConditions(
    @Embedded val encounter: EncounterEntity,
    @Relation(
        entity = CharacterFighterEntity::class,
        parentColumn = "id",
        entityColumn = "encounterId"
    )
    val characters: List<CharacterFighterEntity>,
    @Relation(
        entity = MonsterFighterEntity::class,
        parentColumn = "id",
        entityColumn = "encounterId"
    )
    val monsters: List<MonsterFighterEntity>
)