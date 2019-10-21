import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MessageTest {
    @Test
    fun testEdit() {
        val message = Message(0, "My name is Sasha", 0, 0, false, false)
        message.edit("My name is Alya")
        Assertions.assertTrue(message.isEdited)
        Assertions.assertEquals("My name is Alya", message.text)
    }

    @Test
    fun testDelete() {
        val message = Message(0, "My name is Sasha", 0, 0, false, false)
        message.delete()
        Assertions.assertTrue(message.isDeleted)
        Assertions.assertEquals("This message has been deleted", message.text)
    }
}

