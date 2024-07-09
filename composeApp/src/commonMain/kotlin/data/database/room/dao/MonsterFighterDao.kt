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

    @Query("DELETE FROM fighters WHERE id = :id")
    suspend fun deleteFighter(id: Long): Int
}