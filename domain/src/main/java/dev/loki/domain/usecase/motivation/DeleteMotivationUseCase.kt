package dev.loki.domain.usecase.motivation

import dev.loki.domain.repository.MotivationRepository
import dev.loki.domain.base.BaseUseCase

class DeleteMotivationUseCase(override val repository: MotivationRepository) : BaseUseCase<MotivationRepository> {

}