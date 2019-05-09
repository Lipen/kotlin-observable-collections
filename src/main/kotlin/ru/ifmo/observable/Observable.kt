package ru.ifmo.observable

interface Listener {
    fun update()
}

interface Observable<in T : Listener> {
    fun subscribe(subscriber: T)
    fun updateAll()
}

abstract class AbstractObservable<in T : Listener> : Observable<T> {
    private val subscribers: MutableList<T> = mutableListOf()

    final override fun subscribe(subscriber: T) {
        subscribers.add(subscriber)
    }

    final override fun updateAll() {
        subscribers.forEach(Listener::update)
    }
}

fun Observable<Listener>.subscribe(other: Observable<Listener>) {
    subscribe(object : Listener {
        override fun update() {
            other.updateAll()
        }
    })
}
