package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener

class ObservableMutableIterator<out T>(
    private val delegate: MutableIterator<T>
) : MutableIterator<T> by delegate, AbstractObservable<Listener>() {
    override fun remove() {
        delegate.remove().also { updateAll() }
    }
}
