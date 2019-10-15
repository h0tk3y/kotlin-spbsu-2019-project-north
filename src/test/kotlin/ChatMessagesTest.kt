import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.inject
import org.koin.test.KoinTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class ChatMessagestTest : KoinTest {
    val message1 = Message(0, "Alya is cool", 0, 0, false, false)
    val message2 = Message(0, "Alya is cool version 2", 0, 0, false, false)

    @Test
    fun testAdd() {
        startKoin { modules(daoModule) }
        val chat: ChatMessageDao by inject()
        chat.add(message1)
        Assertions.assertEquals(1, chat.size)
        stopKoin()
    }

    @Test
    fun testGet() {
        startKoin { modules(daoModule) }
        val chat: ChatMessageDao by inject()
        val id = chat.add(message2)
        Assertions.assertEquals(message2, chat.get(id))
        stopKoin()
    }

    @Test
    fun testModify() {
        startKoin { modules(daoModule) }
        val chat: ChatMessageDao by inject()
        val id = chat.add(message1)
        chat.modify(id, message2)
        Assertions.assertEquals(message2, chat.get(id))
        stopKoin()
    }

    @Test
    fun testDelete() {
        startKoin { modules(daoModule) }
        val chat: ChatMessageDao by inject()
        val id1 = chat.add(message1)
        val id2 = chat.add(message2)
        chat.delete(id1)
        Assertions.assertEquals(null, chat.get(id1))
        Assertions.assertEquals(message2, chat.get(id2))
        Assertions.assertEquals(1, chat.size)
        stopKoin()
    }

    @Test
    fun testSearchByText() {
        startKoin { modules(daoModule) }
        val chat: ChatMessageDao by inject()
        val id1 = chat.add(message1)
        val id2 = chat.add(message2)
        Assertions.assertEquals(chat.searchByText("Alya is cool version 2"), listOf(id2))
        Assertions.assertEquals(chat.searchByText("Alya is cool"), listOf(id1, id2))
        chat.delete(id2)
        Assertions.assertEquals(chat.searchByText("Alya"), listOf(id1))
        stopKoin()
    }
}
