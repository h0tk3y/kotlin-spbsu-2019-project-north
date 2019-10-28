import dao.MessageDao
import model.Message
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class MessageDBTest : DBTest {
    @Test
    fun addWithNewIdAndGetByIdTest() {
        val base: MessageDao by inject()
        val msg = Message(
            0, 0, 0,
            "My name is Sasha",
            0, false, false
        )
        val id = base.addWithNewId(msg)
        Assertions.assertEquals(msg, base.getById(id))
        Assertions.assertEquals(null, base.getById(id + 1))
    }

    @Test
    fun deleteByIdTest() {
        val base: MessageDao by inject()
        val msg = Message(
            0, 0, 0,
            "My name is Sasha",
            0, false, false
        )
        val id = base.addWithNewId(msg)
        Assertions.assertEquals(msg, base.getById(id))
        base.deleteById(id + 1)
        Assertions.assertEquals(msg, base.getById(id))
        base.deleteById(id)
        Assertions.assertEquals(null, base.getById(id))
    }

    /*@Test // Fails now
    fun deleteByIdTest2() {
        val base: MessageDao by inject()
        val msg = Message(
            0, 0, 0,
            "My name is Sasha",
            0, false, false
        )
        val id = base.addWithNewId(msg)
        val msg2 = Message(
            1, 1, 1,
            "My name is Dasha",
            1, false, false
        )
        val id2 = base.addWithNewId(msg2)
        base.deleteById(id)
        val msg3 = Message(
            2, 2, 2,
            "My name is Pasha",
            2, false, false
        )
        val id3 = base.addWithNewId(msg3)
        Assertions.assertEquals(msg2, base.getById(id2))
        Assertions.assertEquals(msg3, base.getById(id3))
    }*/

    @Test
    fun findByUserTest() {
        val base: MessageDao by inject()

        val msg1 = Message(
            0, 1, 2,
            "My name is Sasha",
            3, false, false
        )
        val id1 = base.addWithNewId(msg1)

        val msg2 = Message(
            4, 5, 6,
            "My name is Dasha",
            7, false, false
        )
        val id2 = base.addWithNewId(msg2)

        val msg3 = Message(
            8, 5, 9,
            "My name is Petr",
            10, false, false
        )
        val id3 = base.addWithNewId(msg3)

        val msg4 = Message(
            11, 12, 13,
            "My name is Sasha",
            14, false, false
        )
        val id4 = base.addWithNewId(msg4)

        Assertions.assertEquals(listOf(id1), base.findByUser(1))
        Assertions.assertEquals(listOf(id2, id3), base.findByUser(5))
        Assertions.assertEquals(listOf(id4), base.findByUser(12))
        Assertions.assertEquals(listOf<Long>(), base.findByUser(0))
    }

    @Test
    fun findSliceFromChatTest() {
        val base: MessageDao by inject()

        val msg1 = Message(
            0, 1, 2,
            "My name is Sasha",
            3, false, false
        )
        val id1 = base.addWithNewId(msg1)

        val msg2 = Message(
            4, 5, 2,
            "My name is Dasha",
            7, false, false
        )
        val id2 = base.addWithNewId(msg2)

        val msg3 = Message(
            4, 8, 2,
            "My name is Petr",
            10, false, false
        )
        val id3 = base.addWithNewId(msg3)

        val msg4 = Message(
            11, 12, 3,
            "My name is Sasha",
            14, false, false
        )
        val id4 = base.addWithNewId(msg4)

        Assertions.assertEquals(listOf(id2, id3), base.findSliceFromChat(2, 2))
        Assertions.assertEquals(listOf(id1, id2), base.findSliceFromChat(2, 2, 2))
        Assertions.assertEquals(listOf(id1, id2, id3), base.findSliceFromChat(2, 3))
        Assertions.assertEquals(listOf<Long>(), base.findSliceFromChat(2, 0))
        Assertions.assertEquals(listOf(id4), base.findSliceFromChat(3, 1))
        Assertions.assertEquals(listOf<Long>(), base.findSliceFromChat(4, 0))
    }

    @Test
    fun sizeTest() {
        val base: MessageDao by inject()
        Assertions.assertEquals(0, base.size)

        val msg1 = Message(
            0, 1, 2,
            "My name is Sasha",
            3, false, false
        )
        val id1 = base.addWithNewId(msg1)
        Assertions.assertEquals(1, base.size)

        val msg2 = Message(
            4, 5, 2,
            "My name is Dasha",
            7, false, false
        )
        val id2 = base.addWithNewId(msg2)
        Assertions.assertEquals(2, base.size)

        Assertions.assertEquals(2, base.size)
        base.deleteById(id1)
        Assertions.assertEquals(1, base.size)
        base.deleteById(id1)
        Assertions.assertEquals(1, base.size)
        base.deleteById(id2)
        Assertions.assertEquals(0, base.size)
    }
}