package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe

class ObservableMutableCollection<E> private constructor(
    private val delegate: MutableCollection<E>,
    private val iterable: ObservableMutableIterable<E>
) : MutableCollection<E> by delegate,
    MutableIterable<E> by iterable,
    AbstractObservable<Listener>() {

    constructor(delegate: MutableCollection<E>) : this(delegate, ObservableMutableIterable(delegate)) {
        iterable.subscribe(this)
    }

    override fun iterator(): MutableIterator<E> {
        return iterable.iterator()
    }

    override fun add(element: E): Boolean {
        return delegate.add(element).also { updateAll() }
    }

    override fun remove(element: E): Boolean {
        return delegate.remove(element).also { updateAll() }
    }

    override fun addAll(elements: Collection<E>): Boolean {
        return delegate.addAll(elements).also { updateAll() }
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        return delegate.removeAll(elements).also { updateAll() }
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return delegate.retainAll(elements).also { updateAll() }
    }

    override fun clear() {
        delegate.clear().also { updateAll() }
    }

    override fun toString(): String {
        return delegate.toString()
    }
}
