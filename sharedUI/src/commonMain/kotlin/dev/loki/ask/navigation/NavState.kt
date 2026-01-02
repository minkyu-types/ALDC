package dev.loki.ask.navigation

data class NavState<T: NavKey>(
    val backstack: List<T> = emptyList()
) {
    val current: T? get() = backstack.lastOrNull()
    
    fun push(key: T) = copy(backstack = backstack + key)
    
    fun pop(): NavState<T> =
        if (backstack.size > 1) copy(backstack = backstack.dropLast(1)) else this
    
    fun canPop(): Boolean = backstack.size > 1
    
    fun replace(key: T) = copy(backstack = backstack.dropLast(1) + key)
    
    fun popUntil(predicate: (T) -> Boolean): NavState<T> {
        val index = backstack.indexOfLast(predicate)
        return if (index >= 0) {
            copy(backstack = backstack.take(index + 1))
        } else {
            this
        }
    }
}