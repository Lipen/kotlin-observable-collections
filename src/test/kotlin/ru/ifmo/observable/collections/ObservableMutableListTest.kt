package ru.ifmo.observable.collections

import org.amshove.kluent.invoking
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContain
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
        updated = false
        list = ObservableMutableList<Int>().also { it.subscribe(listener) }
    }

    @Test
    fun add() {
        list.add(42)

        updated.shouldBeTrue()
        list.shouldContain(42)
    }

    @Test
    fun `add by index`() {
        list.add(0, 42)

        updated.shouldBeTrue()
        list shouldContainSame listOf(42)
    }

    @Test
    fun `add by large index`() {
        invoking {
            list.add(1, 42)
        } shouldThrow IndexOutOfBoundsException::class

        list.shouldBeEmpty()
    }

    @Test
    fun addAll() {
        list.addAll(listOf(4, 2, 5))

        updated.shouldBeTrue()
        list shouldContainSame listOf(4, 2, 5)
    }

    @Test
    fun `addAll by index`() {
        list.addAll(0, listOf(4, 2, 5))

        updated.shouldBeTrue()
        list shouldContainSame listOf(4, 2, 5)
    }

    @Test
    fun `addAll by large index`() {
        invoking {
            list.addAll(1, listOf(4, 2, 5))
        } shouldThrow IndexOutOfBoundsException::class
        updated.shouldBeFalse()
        list.shouldBeEmpty()
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
        iterator.add(42)

        updated.shouldBeTrue()
        list shouldContainSame listOf(42)
    }

    @Test
    fun `listIterator by index`() {
        val iterator: MutableListIterator<Int> = list.listIterator(0)
        iterator.add(42)

        updated.shouldBeTrue()
        list shouldContainSame listOf(42)
    }

    @Test
    fun `listIterator by large index`() {
        invoking {
            list.listIterator(1)
        } shouldThrow IndexOutOfBoundsException::class
        updated.shouldBeFalse()
        list.shouldBeEmpty()
    }

    @Test
    fun remove() {
        list.add(5)
        list.add(42)
        list.add(17)
        updated = false

        list.remove(42)

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 17)
    }

    @Test
    fun removeAll() {
        list.add(5)
        list.add(42)
        list.add(17)
        updated = false

        list.removeAll(listOf(5, 17))

        updated.shouldBeTrue()
        list shouldContainSame listOf(42)
    }

    @Test
    fun removeAt() {
        list.add(5)
        list.add(42)
        list.add(17)
        updated = false

        list.removeAt(2)

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 42)
    }

    @Test
    fun removeIf() {
        list.add(5)
        list.add(42)
        list.add(17)
        updated = false

        list.removeIf { it == 42 }

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 17)
    }

    @Test
    fun replaceAll() {
        list.add(5)
        list.add(42)
        list.add(17)
        updated = false

        list.replaceAll { it * 2 }

        updated.shouldBeTrue()
        list shouldContainSame listOf(10, 84, 34)
    }

    @Test
    fun retainAll() {
        list.add(5)
        list.add(42)
        list.add(17)
        updated = false

        list.retainAll(listOf(17, 5, 30))

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 17)
    }

    @Test
    fun `set by index`() {
        list.add(5)
        list.add(42)
        list.add(17)
        updated = false

        list[1] = 41

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 41, 17)
    }

    @Test
    fun `set by large index`() {
        list.add(5)
        list.add(42)
        list.add(17)
        updated = false

        invoking {
            list.set(3, 10)
        } shouldThrow IndexOutOfBoundsException::class

        updated.shouldBeFalse()
        list shouldContainSame listOf(5, 42, 17)
    }

    @Test
    fun `get subList`() {
        list.add(5)
        list.add(42)
        list.add(17)
        list.add(6)
        list.add(89)
        updated = false

        val sub: List<Int> = list.subList(1, 4)

        updated.shouldBeFalse()
        list shouldContainSame listOf(5, 17, 42, 6, 89)
        sub shouldContainSame listOf(17, 42, 6)
    }

    @Test
    fun `get and modify subList`() {
        list.add(5)
        list.add(42)
        list.add(17)
        list.add(6)
        list.add(89)
        updated = false

        val sub: MutableList<Int> = list.subList(1, 4)
        sub[0] = 10

        updated.shouldBeTrue()
        list shouldContainSame listOf(5, 10, 17, 6, 89)
        sub shouldContainSame listOf(10, 17, 6)
    }

    @Test
    fun toString_() {
        list.add(5)
        list.add(42)
        list.add(17)

        list.toString() shouldEqual "[5, 42, 17]"
    }
}
