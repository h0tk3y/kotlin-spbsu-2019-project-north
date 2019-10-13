import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals

class ChaMessagestTest {
    @Test
    fun absoluteTruth() {
        assertEquals(2, 1 + 1, "hmmmmmmmmmmm. 1 + 1 = 2")
    }
    @Test
    fun testGet() {
        val mDAO = MessageDao
        val chatik = ChatMessages(MessageDao)

    }
    @Test
    fun testDelete() {
        val message = Message(0, "My name is Sasha", 0, 1, false, false)
        message.delete()
        Assertions.assertTrue(message.isDeleted && message.text == "This message has been deleted")
    }
}