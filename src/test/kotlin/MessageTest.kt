import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals


class MessageTest {
    @Test
    fun absoluteTruth() {
        assertEquals(2, 1 + 1, "hmmmmmmmmmmm. 1 + 1 = 2")
    }
    @Test
    fun testEdit() {
        val message = Message(0, "My name is Sasha", 0, 0, false, false)
        message.edit("My name is Alya")
        Assertions.assertTrue(message.isEdited && message.text == "My name is Alya")
    }
    @Test
    fun testDelete() {
        val message = Message(0, "My name is Sasha", 0, 0, false, false)
        message.delete()
        Assertions.assertTrue(message.isDeleted && message.text == "This message has been deleted")
    }
}