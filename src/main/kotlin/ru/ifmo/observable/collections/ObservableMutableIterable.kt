package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe

class ObservableMutableIterable<out T>(
    private val delegate: MutableIterable<T>
) : MutableIterable<T>, AbstractObservable<Listener>() {
    override fun iterator(): MutableIterator<T> {
        return ObservableMutableIterator(delegate.iterator()).also { it.subscribe(this) }
    }
}
