package data.database.room

import androidx.room.TypeConverter
import domain.model.monster.Challenge

class ChallengeTypeConverter {

    @TypeConverter
    fun fromChallenge(challenge: Challenge): String = challenge.name

    @TypeConverter
    fun toChallenge(name: String): Challenge = Challenge.valueOf(name)

}