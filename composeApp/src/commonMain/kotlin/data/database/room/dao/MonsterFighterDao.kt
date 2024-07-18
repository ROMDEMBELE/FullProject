package data.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import data.database.room.entity.MonsterFighterEntity

@Dao
interface MonsterFighterDao {

    @Insert
    suspend fun insertFighter(fighter: MonsterFighterEntity): Long

    @Update
    suspend fun updateFighter(fighter: MonsterFighterEntity): Int

    @Query("DELETE FROM monster_fighters WHERE id = :id")
    suspend fun deleteFighter(id: Long): Int

    @Query("SELECT * FROM monster_fighters WHERE encounterId = :encounterId")
    suspend fun getFightersByEncounterId(encounterId: Long): List<MonsterFighterEntity>

    @Query("DELETE FROM monster_fighters WHERE encounterId = :encounterId")
    suspend fun deleteFightersByEncounterId(encounterId: Long): Int
}