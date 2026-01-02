package dev.loki.domain.usecase.calendar

import dev.loki.domain.repository.CalendarRepository
import dev.loki.domain.base.BaseUseCase

class GetMonthlyDataUseCase(override val repository: CalendarRepository) : BaseUseCase<CalendarRepository> {
}