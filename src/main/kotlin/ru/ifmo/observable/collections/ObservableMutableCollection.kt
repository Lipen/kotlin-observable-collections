package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe
import java.util.function.Predicate

class ObservableMutableCollection<E>(
    private val delegate: MutableCollection<E>
) : MutableCollection<E> by delegate, AbstractObservable<Listener>() {
    override fun add(element: E): Boolean {
        return delegate.add(element).also { updateAll() }
    }

    override fun addAll(elements: Collection<E>): Boolean {
        return delegate.addAll(elements).also { updateAll() }
    }

    override fun clear() {
        delegate.clear().also { updateAll() }
    }

    override fun iterator(): MutableIterator<E> {
        return ObservableMutableIterator(delegate.iterator()).also { it.subscribe(this) }
    }

    override fun remove(element: E): Boolean {
        return delegate.remove(element).also { updateAll() }
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        return delegate.removeAll(elements).also { updateAll() }
    }

    override fun removeIf(filter: Predicate<in E>): Boolean {
        return delegate.removeIf(filter)
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return delegate.retainAll(elements).also { updateAll() }
    }
}
