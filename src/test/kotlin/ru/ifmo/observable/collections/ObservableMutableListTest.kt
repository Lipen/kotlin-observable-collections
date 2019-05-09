package ru.ifmo.observable.collections

import org.amshove.kluent.invoking
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ru.ifmo.observable.Listener

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ObservableMutableListTest {

    private var updated = false
    private val listener = object : Listener {
        override fun update() {
            updated = true
        }
    }
    private lateinit var list: MutableList<Int>

    @BeforeEach
    fun setUp() {
        list = ObservableMutableList(mutableListOf(5, 42, 17)).also { it.subscribe(listener) }
        updated = false
    }

    @Test
    fun add() {
        list.add(42)

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 42, 17, 42)
    }

    @Test
    fun `add by index`() {
        list.add(0, 42)

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 42, 17, 42)
    }

    @Test
    fun `add by large index`() {
        invoking {
            list.add(4, 42)
        } shouldThrow IndexOutOfBoundsException::class

        list shouldContainSame listOf(5, 42, 17)
    }

    @Test
    fun addAll() {
        list.addAll(listOf(44, 8))

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 42, 17, 44, 8)
    }

    @Test
    fun `addAll by index`() {
        list.addAll(1, listOf(44, 8))

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 44, 8, 42, 17)
    }

    @Test
    fun `addAll by large index`() {
        invoking {
            list.addAll(4, listOf(44, 8))
        } shouldThrow IndexOutOfBoundsException::class
        updated.shouldBeFalse()
        list shouldContainSame listOf(5, 42, 17)
    }

    @Test
    fun clear() {
        list.clear()

        updated.shouldBeTrue()
        list.shouldBeEmpty()
    }

    @Test
    fun listIterator() {
        val iterator: MutableListIterator<Int> = list.listIterator()
        iterator.add(9)

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 42, 17, 9)
    }

    @Test
    fun `listIterator by index`() {
        val iterator: MutableListIterator<Int> = list.listIterator(2)
        iterator.add(9)

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 42, 9, 17)
    }

    @Test
    fun `listIterator by large index`() {
        invoking {
            list.listIterator(3)
        } //shouldThrow IndexOutOfBoundsException::class
        updated.shouldBeFalse()
        list shouldContainSame listOf(5, 42, 17)
    }

    @Test
    fun remove() {
        list.remove(42)

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 17)
    }

    @Test
    fun removeAll() {
        list.removeAll(listOf(5, 17))

        updated.shouldBeTrue()
        list shouldContainSame listOf(42)
    }

    @Test
    fun removeAt() {
        list.removeAt(2)

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 42)
    }

    @Test
    fun removeIf() {
        list.removeIf { it == 42 }

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 17)
    }

    @Test
    fun replaceAll() {
        list.replaceAll { it * 2 }

        updated.shouldBeTrue()
        list shouldContainSame listOf(10, 84, 34)
    }

    @Test
    fun retainAll() {
        list.retainAll(listOf(17, 5, 30))

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 17)
    }

    @Test
    fun `set by index`() {
        list[1] = 41

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 41, 17)
    }

    @Test
    fun `set by large index`() {
        invoking {
            list.set(3, 10)
        } shouldThrow IndexOutOfBoundsException::class
        updated.shouldBeFalse()
        list shouldContainSame listOf(5, 42, 17)
    }

    @Test
    fun `get subList`() {
        val sub: List<Int> = list.subList(1, 3)

        updated.shouldBeFalse()
        list shouldContainSame listOf(5, 42, 17)
        sub shouldContainSame listOf(42, 17)
    }

    @Test
    fun `get and modify subList`() {
        val sub: MutableList<Int> = list.subList(1, 3)
        sub[0] = 10

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 10, 17)
        sub shouldContainSame listOf(10, 17)
    }

    @Test
    fun toString_() {
        list.toString() shouldEqual "[5, 42, 17]"
    }
}
