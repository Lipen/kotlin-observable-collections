package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe

class ObservableMutableSet<E> private constructor(
    private val delegate: MutableSet<E>,
    private val collection: ObservableMutableCollection<E>
) : MutableSet<E>,
    MutableCollection<E> by collection,
    AbstractObservable<Listener>() {

    constructor(delegate: MutableSet<E> = mutableSetOf()) :
        this(delegate, ObservableMutableCollection(delegate)) {
        collection.subscribe(this)
    }

    override fun toString(): String {
        return delegate.toString()
    }
}
