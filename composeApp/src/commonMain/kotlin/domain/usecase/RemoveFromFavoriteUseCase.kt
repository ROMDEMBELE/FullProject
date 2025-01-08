package domain.usecase

import domain.repository.FavoriteRepository

class RemoveFromFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {

    operator fun invoke(slug: String) {
        favoriteRepository.removeFromFavorite(slug)
    }
}