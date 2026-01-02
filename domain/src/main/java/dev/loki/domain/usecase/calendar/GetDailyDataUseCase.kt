package dev.loki.domain.usecase.calendar

import dev.loki.domain.base.BaseUseCase
import dev.loki.domain.repository.CalendarRepository

class GetDailyDataUseCase(override val repository: CalendarRepository) : BaseUseCase<CalendarRepository> {
}