package domain.repository

import domain.model.Feat
import kotlinx.coroutines.flow.Flow

interface FeatRepository {

    fun getAllFeat(): Flow<List<Feat>>

}