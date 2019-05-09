package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe
import java.util.Comparator
import java.util.function.Predicate
import java.util.function.UnaryOperator

class ObservableMutableList<E>(
    private val delegate: MutableList<E> = mutableListOf()
) : MutableList<E> by delegate, AbstractObservable<Listener>() {
    override fun add(element: E): Boolean {
        return delegate.add(element).also { updateAll() }
    }

    override fun add(index: Int, element: E) {
        return delegate.add(index, element).also { updateAll() }
    }

    override fun addAll(elements: Collection<E>): Boolean {
        return delegate.addAll(elements).also { updateAll() }
    }

    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        return delegate.addAll(index, elements).also { updateAll() }
    }

    override fun clear() {
        delegate.clear().also { updateAll() }
    }

    override fun listIterator(): MutableListIterator<E> {
        return ObservableMutableListIterator(delegate.listIterator()).also { it.subscribe(this) }
    }

    override fun listIterator(index: Int): MutableListIterator<E> {
        return ObservableMutableListIterator(delegate.listIterator(index)).also { it.subscribe(this) }
    }

    override fun remove(element: E): Boolean {
        return delegate.remove(element).also { updateAll() }
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        return delegate.removeAll(elements).also { updateAll() }
    }

    override fun removeAt(index: Int): E {
        return delegate.removeAt(index).also { updateAll() }
    }

    override fun removeIf(filter: Predicate<in E>): Boolean {
        return delegate.removeIf(filter).also { updateAll() }
    }

    override fun replaceAll(operator: UnaryOperator<E>) {
        delegate.replaceAll(operator).also { updateAll() }
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return delegate.retainAll(elements).also { updateAll() }
    }

    override fun set(index: Int, element: E): E {
        return delegate.set(index, element).also { updateAll() }
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
        return ObservableMutableList(delegate.subList(fromIndex, toIndex)).also { it.subscribe(this) }
    }

    override fun toString(): String {
        return delegate.toString()
    }
}
