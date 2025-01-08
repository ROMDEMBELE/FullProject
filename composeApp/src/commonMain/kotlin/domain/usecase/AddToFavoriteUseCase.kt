package domain.usecase

import domain.repository.FavoriteRepository

class AddToFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {

    operator fun invoke(slug: String) {
        favoriteRepository.addToFavorite(slug)
    }
}