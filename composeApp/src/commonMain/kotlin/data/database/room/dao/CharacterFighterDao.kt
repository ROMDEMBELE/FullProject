package data.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import data.database.room.entity.CharacterFighterEntity

@Dao
interface CharacterFighterDao {

    @Insert
    suspend fun insertFighter(fighter: CharacterFighterEntity): Long

    @Update
    suspend fun updateFighter(fighter: CharacterFighterEntity): Int

    @Query("DELETE FROM character_fighters WHERE id = :id")
    suspend fun deleteFighter(id: Long): Int

    @Query("SELECT * FROM character_fighters WHERE encounterId = :encounterId")
    suspend fun getFightersByEncounterId(encounterId: Long): List<CharacterFighterEntity>

    @Query("DELETE FROM character_fighters WHERE encounterId = :encounterId")
    suspend fun deleteFightersByEncounterId(encounterId: Long): Int
}