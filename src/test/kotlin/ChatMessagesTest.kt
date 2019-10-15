import org.junit.jupiter.api.*
import org.koin.core.inject
import org.koin.test.KoinTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class ChatMessagestTest : KoinTest {
    val message1 = Message(0, "Alya is cool", 0, 0, false, false)
    val message2 = Message(0, "Alya is cool version 2", 0, 0, false, false)

    @BeforeEach
    fun start() {
        startKoin { modules(daoModule) }
    }

    @AfterEach
    fun stop() {
        stopKoin()
    }

    @Test
    fun testAdd() {
        val chat: ChatMessageDao by inject()
        chat.add(message1)
        Assertions.assertEquals(1, chat.size)
    }

    @Test
    fun testGet() {
        val chat: ChatMessageDao by inject()
        val id = chat.add(message2)
        Assertions.assertEquals(message2, chat.get(id))
    }

    @Test
    fun testModify() {
        val chat: ChatMessageDao by inject()
        val id = chat.add(message1)
        chat.modify(id, message2)
        Assertions.assertEquals(message2, chat.get(id))
    }

    @Test
    fun testDelete() {
        val chat: ChatMessageDao by inject()
        val id1 = chat.add(message1)
        val id2 = chat.add(message2)
        chat.delete(id1)
        Assertions.assertEquals(null, chat.get(id1))
        Assertions.assertEquals(message2, chat.get(id2))
        Assertions.assertEquals(1, chat.size)
    }

    @Test
    fun testSearchByText() {
        val chat: ChatMessageDao by inject()
        val id1 = chat.add(message1)
        val id2 = chat.add(message2)
        Assertions.assertEquals(chat.searchByText("Alya is cool version 2"), listOf(id2))
        Assertions.assertEquals(chat.searchByText("Alya is cool"), listOf(id1, id2))
        chat.delete(id2)
        Assertions.assertEquals(chat.searchByText("Alya"), listOf(id1))
    }
}
