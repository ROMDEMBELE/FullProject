package domain.repository

import domain.model.feat.Feat
import kotlinx.coroutines.flow.Flow

interface FeatRepository {

    fun getAllFeat(): Flow<List<Feat>>

}