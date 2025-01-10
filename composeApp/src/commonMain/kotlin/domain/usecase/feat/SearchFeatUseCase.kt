package domain.usecase.feat

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.Feat
import domain.repository.FeatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchFeatUseCase(private val repository: FeatRepository) {

    operator fun invoke(query: TextFieldValue): Flow<List<Feat>> = repository.getAllFeat()
        .map { list ->
            list.filter { feat ->
                feat.name.contains(query.text, ignoreCase = true)
            }.sortedBy { feat -> feat.name }
        }

}