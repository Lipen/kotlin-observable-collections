package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe

class ObservableMutableMap<K, V>(
    private val delegate: MutableMap<K, V> = mutableMapOf()
) : MutableMap<K, V>,
    Map<K, V> by delegate,
    AbstractObservable<Listener>() {

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> =
        ObservableMutableSet(
            delegate.entries
                .map { entry ->
                    ObservableMutableEntry(entry)
                        .also { it.subscribe(this) }
                        as MutableMap.MutableEntry<K, V>
                }
                .toMutableSet()
        ).also { it.subscribe(this) }

    override val keys: MutableSet<K> =
        ObservableMutableSet(delegate.keys).also { it.subscribe(this) }

    override val values: MutableCollection<V> =
        ObservableMutableCollection(delegate.values).also { it.subscribe(this) }

    override fun put(key: K, value: V): V? {
        return delegate.put(key, value).also { updateAll() }
    }

    override fun remove(key: K): V? {
        return delegate.remove(key).also { updateAll() }
    }

    override fun remove(key: K, value: V): Boolean {
        return delegate.remove(key, value).also { updateAll() }
    }

    override fun putAll(from: Map<out K, V>) {
        return delegate.putAll(from).also { updateAll() }
    }

    override fun clear() {
        delegate.clear().also { updateAll() }
    }

    override fun toString(): String {
        return delegate.toString()
    }

    private class ObservableMutableEntry<K, V>(
        private val delegate: MutableMap.MutableEntry<K, V>
    ) : MutableMap.MutableEntry<K, V> by delegate, AbstractObservable<Listener>() {
        override fun setValue(newValue: V): V {
            return delegate.setValue(newValue).also { updateAll() }
        }
    }
}
