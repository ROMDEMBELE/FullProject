package data.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import data.database.room.entity.EncounterEntity
import data.database.room.entity.EncounterWithFightersAndConditions
import kotlinx.coroutines.flow.Flow

@Dao
interface EncounterDao {

    @Insert
    suspend fun insertEncounter(encounter: EncounterEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEncounter(encounter: EncounterEntity): Int

    @Transaction
    @Query("SELECT * FROM encounters")
    fun getAll(): Flow<List<EncounterEntity>>

    @Transaction
    @Query("SELECT * FROM encounters WHERE id = :id")
    fun getEncounterWithFightersAndConditions(id: Long): Flow<EncounterWithFightersAndConditions?>

    @Transaction
    @Query("SELECT * FROM encounters WHERE campaignId = :campaignId")
    fun getEncountersByCampaignId(campaignId: Long): Flow<List<EncounterWithFightersAndConditions>>
}