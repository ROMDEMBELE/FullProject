package domain.model.monster

open class MonsterReference(
    open val key: String,
    open val name: String,
    open val isFavorite: Boolean = false,
    open val challenge: Challenge
)