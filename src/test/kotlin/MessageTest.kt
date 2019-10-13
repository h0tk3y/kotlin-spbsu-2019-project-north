import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MessageTest {
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
