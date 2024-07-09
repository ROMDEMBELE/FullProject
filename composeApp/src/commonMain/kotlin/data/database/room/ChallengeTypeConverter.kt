package data.database.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import domain.model.monster.Challenge

@ProvidedTypeConverter
class ChallengeTypeConverter {

    @TypeConverter
    fun fromChallenge(challenge: Challenge): String = challenge.name

    @TypeConverter
    fun toChallenge(name: String): Challenge = Challenge.valueOf(name)

}