package dev.loki.domain.usecase

import dev.loki.domain.repository.BaseRepository

interface BaseUseCase<out R: BaseRepository>{
    val repository: R
}