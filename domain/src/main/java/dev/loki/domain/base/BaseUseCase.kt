package dev.loki.domain.base

interface BaseUseCase<out R: BaseRepository>{
    val repository: R
}