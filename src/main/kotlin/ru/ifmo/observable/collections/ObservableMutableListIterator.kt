package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener

class ObservableMutableListIterator<T>(
    private val delegate: MutableListIterator<T>
) : MutableListIterator<T> by delegate, AbstractObservable<Listener>() {
    override fun add(element: T) {
        delegate.add(element).also { updateAll() }
    }

    override fun hasNext(): Boolean {
        return delegate.hasNext().also { updateAll() }
    }

    override fun next(): T {
        return delegate.next().also { updateAll() }
    }

    override fun remove() {
        delegate.remove().also { updateAll() }
    }

    override fun set(element: T) {
        delegate.set(element).also { updateAll() }
    }
}
