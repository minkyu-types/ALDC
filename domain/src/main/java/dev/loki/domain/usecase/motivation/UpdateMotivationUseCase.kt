package dev.loki.domain.usecase.motivation

import dev.loki.domain.repository.MotivationRepository
import dev.loki.domain.base.BaseUseCase

class UpdateMotivationUseCase(override val repository: MotivationRepository) : BaseUseCase<MotivationRepository> {
}