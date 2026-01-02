package dev.loki.domain.usecase.user

import dev.loki.domain.repository.UserRepository
import dev.loki.domain.usecase.BaseUseCase

class SignUpUseCase(override val repository: UserRepository) : BaseUseCase<UserRepository> {
}