package dev.loki.domain.usecase.quote

import dev.loki.domain.repository.QuoteRepository
import dev.loki.domain.base.BaseUseCase

class GetTodayQuoteUseCase(override val repository: QuoteRepository) : BaseUseCase<QuoteRepository> {

}