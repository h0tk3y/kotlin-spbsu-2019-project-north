import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ChatMessagestTest {
    val message1 = Message(0, "Alya is cool", 0, 0, false, false)
    val message2 = Message(0, "Alya is cool version 2", 0, 0, false, false)
    val base = MessageDB

    @Test
    fun testAdd() {
        val chat = ChatMessages(base)
        chat.add(message1)
        Assertions.assertEquals(chat.messageList.size, 1)
    }

    @Test
    fun testGet() {
        val chat = ChatMessages(base)
        val id = chat.add(message2)
        Assertions.assertEquals(chat.get(id), message2)
    }

    @Test
    fun testModify() {
        val chat = ChatMessages(base)
        val id = chat.add(message1)
        chat.modify(id, message2)
        Assertions.assertEquals(chat.get(id), message2)
    }

    @Test
    fun testDelete() {
        val chat = ChatMessages(base)
        val id1 = chat.add(message1)
        val id2 = chat.add(message2)
        chat.delete(id1)
        Assertions.assertEquals(chat.get(id1), null)
        Assertions.assertEquals(chat.get(id2), message2)
        Assertions.assertEquals(chat.messageList.size, 1)
    }

    @Test
    fun testSearchByText() {
        val chat = ChatMessages(base)
        val id1 = chat.add(message1)
        val id2 = chat.add(message2)
        Assertions.assertEquals(chat.searchByText("Alya is cool version 2"), listOf(id2))
        Assertions.assertEquals(chat.searchByText("Alya is cool"), listOf(id1, id2))
        chat.delete(id2)
        Assertions.assertEquals(chat.searchByText("Alya"), listOf(id1))
    }
}
