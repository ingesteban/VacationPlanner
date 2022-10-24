package dev.esteban.places.domain.usecase

import dev.esteban.common.utils.UseCase
import dev.esteban.places.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher

class UpdatePlaceVisitedUseCase(
    ioDispatcher: CoroutineDispatcher,
    private val placeRepository: PlaceRepository
) : UseCase<Parameters, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: Parameters) {
        return placeRepository.updatePlaceVisited(
            id = parameters.id,
            visited = parameters.visited
        )
    }
}

data class Parameters(
    val id: Long,
    val visited: Boolean
)
