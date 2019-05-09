package ru.ifmo.observable.collections

import ru.ifmo.observable.AbstractObservable
import ru.ifmo.observable.Listener
import ru.ifmo.observable.subscribe
import java.util.function.BiFunction
import java.util.function.Function

class ObservableMutableMap<K, V>(
    private val delegate: MutableMap<K, V> = mutableMapOf()
) : MutableMap<K, V> by delegate, AbstractObservable<Listener>() {
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> =
        ObservableMutableSet(delegate.entries).also { it.subscribe(this) }
    override val keys: MutableSet<K> =
        ObservableMutableSet(delegate.keys).also { it.subscribe(this) }
    override val values: MutableCollection<V> =
        ObservableMutableCollection(delegate.values).also { it.subscribe(this) }

    override fun clear() {
        delegate.clear().also { updateAll() }
    }

    override fun compute(key: K, remappingFunction: BiFunction<in K, in V?, out V?>): V? {
        return delegate.compute(key, remappingFunction).also { updateAll() }
    }

    override fun computeIfAbsent(key: K, mappingFunction: Function<in K, out V>): V {
        return delegate.computeIfAbsent(key, mappingFunction)
    }

    override fun computeIfPresent(key: K, remappingFunction: BiFunction<in K, in V, out V?>): V? {
        return delegate.computeIfPresent(key, remappingFunction).also { updateAll() }
    }

    override fun merge(key: K, value: V, remappingFunction: BiFunction<in V, in V, out V?>): V? {
        return delegate.merge(key, value, remappingFunction).also { updateAll() }
    }

    override fun put(key: K, value: V): V? {
        return delegate.put(key, value).also { updateAll() }
    }

    override fun putAll(from: Map<out K, V>) {
        delegate.putAll(from).also { updateAll() }
    }

    override fun putIfAbsent(key: K, value: V): V? {
        return delegate.putIfAbsent(key, value).also { updateAll() }
    }

    override fun remove(key: K): V? {
        return delegate.remove(key).also { updateAll() }
    }

    override fun remove(key: K, value: V): Boolean {
        return delegate.remove(key, value).also { updateAll() }
    }

    override fun replace(key: K, oldValue: V, newValue: V): Boolean {
        return delegate.replace(key, oldValue, newValue).also { updateAll() }
    }

    override fun replace(key: K, value: V): V? {
        return delegate.replace(key, value).also { updateAll() }
    }

    override fun replaceAll(function: BiFunction<in K, in V, out V>) {
        delegate.replaceAll(function).also { updateAll() }
    }

    override fun toString(): String {
        return delegate.toString()
    }
}
