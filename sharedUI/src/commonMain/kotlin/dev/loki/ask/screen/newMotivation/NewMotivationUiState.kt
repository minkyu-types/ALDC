package dev.loki.ask.screen.newMotivation

import dev.loki.ask.model.HierarchicalModel

data class NewMotivationUiState<T, Child>(
    val motivation: T,
) where T : HierarchicalModel<Child> {
    val childs: List<Child> get() = motivation.children
}
