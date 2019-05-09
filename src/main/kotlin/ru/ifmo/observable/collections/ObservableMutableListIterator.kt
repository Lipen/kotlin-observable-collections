package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe

class ObservableMutableListIterator<T>(
    private val delegate: MutableListIterator<T>,
    private val iterator: ObservableMutableIterator<T>
) : MutableListIterator<T> by delegate,
    MutableIterator<T> by iterator,
    AbstractObservable<Listener>() {

    constructor(delegate: MutableListIterator<T>) :
        this(delegate, ObservableMutableIterator(delegate)) {
        iterator.subscribe(this)
    }

    override fun hasNext(): Boolean {
        return iterator.hasNext()
    }

    override fun next(): T {
        return iterator.next()
    }

    override fun remove() {
        iterator.remove()
    }

    override fun set(element: T) {
        delegate.set(element).also { updateAll() }
    }

    override fun add(element: T) {
        delegate.add(element).also { updateAll() }
    }
}
