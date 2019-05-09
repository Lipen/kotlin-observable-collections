package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe

class ObservableMutableIterable<out T>(
    val delegate: MutableIterable<T>
) : MutableIterable<T> by delegate, AbstractObservable<Listener>() {
    override fun iterator(): MutableIterator<T> {
        return ObservableMutableIterator(delegate.iterator()).also { it.subscribe(this) }
    }
}
