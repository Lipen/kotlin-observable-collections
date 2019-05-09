package ru.ifmo.observable.collections

import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.observable.Listener

internal class ObservableMutableMapTest {

    private var updated = false
    private val listener = object : Listener {
        override fun update() {
            updated = true
        }
    }
    private lateinit var map: MutableMap<String, Int>

    @BeforeEach
    internal fun setUp() {
        map = ObservableMutableMap(
            mutableMapOf(
                "Hello" to 42,
                "zero" to 0,
                "one" to 1
            )
        ).also { it.subscribe(listener) }
        updated = false
    }

    @Test
    fun put() {
        map["hey"] = 9

        updated.shouldBeTrue()
        map.keys shouldContainSame listOf("Hello", "zero", "one", "hey")
        map.values shouldContainSame listOf(42, 0, 1, 9)
    }

    @Test
    fun putAll() {
        map.putAll(mapOf("x" to 2, "y" to 9))

        updated.shouldBeTrue()
        map.keys shouldContainSame listOf("Hello", "zero", "one", "x", "y")
        map.values shouldContainSame listOf(42, 0, 1, 2, 9)
    }

    @Test
    fun remove() {
        map.remove("zero") shouldEqual 0

        updated.shouldBeTrue()
        map.keys shouldContainSame listOf("Hello", "one")
        map.values shouldContainSame listOf(42, 1)
    }

    @Test
    fun `remove present value`() {
        map.remove("zero", 0).shouldBeTrue()

        updated.shouldBeTrue()
        map.keys shouldContainSame listOf("Hello", "one")
        map.values shouldContainSame listOf(42, 1)
    }

    @Test
    fun `remove absent value`() {
        map.remove("zero", 99).shouldBeFalse()

        updated.shouldBeTrue()
        map.keys shouldContainSame listOf("Hello", "zero", "one")
        map.values shouldContainSame listOf(42, 0, 1)
    }

    @Test
    fun clear() {
        map.clear()

        updated.shouldBeTrue()
        map.shouldBeEmpty()
        map.keys.shouldBeEmpty()
        map.values.shouldBeEmpty()
    }

    @Test
    fun toString_() {
        map.toString() shouldEqual "{Hello=42, zero=0, one=1}"
    }
}
