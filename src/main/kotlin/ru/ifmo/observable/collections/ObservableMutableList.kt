package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe

class ObservableMutableList<E> private constructor(
    private val delegate: MutableList<E>,
    private val collection: ObservableMutableCollection<E>
) : MutableList<E> by delegate,
    MutableCollection<E> by collection,
    AbstractObservable<Listener>() {

    override val size: Int
        get() = collection.size

    constructor (delegate: MutableList<E> = mutableListOf()) :
        this(delegate, ObservableMutableCollection(delegate)) {
        collection.subscribe(this)
    }

    override fun contains(element: E): Boolean {
        return collection.contains(element)
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return collection.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return collection.isEmpty()
    }

    override fun iterator(): MutableIterator<E> {
        return collection.iterator()
    }

    override fun add(element: E): Boolean {
        return collection.add(element)
    }

    override fun addAll(elements: Collection<E>): Boolean {
        return collection.addAll(elements)
    }

    override fun clear() {
        collection.clear()
    }

    override fun remove(element: E): Boolean {
        return collection.remove(element)
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        return collection.removeAll(elements)
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return collection.retainAll(elements)
    }

    override fun add(index: Int, element: E) {
        return delegate.add(index, element).also { updateAll() }
    }

    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        return delegate.addAll(index, elements).also { updateAll() }
    }

    override fun removeAt(index: Int): E {
        return delegate.removeAt(index).also { updateAll() }
    }

    override fun set(index: Int, element: E): E {
        return delegate.set(index, element).also { updateAll() }
    }

    override fun listIterator(): MutableListIterator<E> {
        return ObservableMutableListIterator(delegate.listIterator()).also { it.subscribe(this) }
    }

    override fun listIterator(index: Int): MutableListIterator<E> {
        return ObservableMutableListIterator(delegate.listIterator(index)).also { it.subscribe(this) }
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
        return ObservableMutableList(delegate.subList(fromIndex, toIndex)).also { it.subscribe(this) }
    }

    override fun toString(): String {
        return delegate.toString()
    }
}
