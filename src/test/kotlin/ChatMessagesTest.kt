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
        chat.addWithNewId(message1)
        Assertions.assertEquals(1, chat.size)
    }

    @Test
    fun testGet() {
        val chat: ChatMessageDao by inject()
        val id = chat.addWithNewId(message2)
        Assertions.assertEquals(message2, chat.getById(id))
    }

    @Test
    fun testModify() {
        val chat: ChatMessageDao by inject()
        val id = chat.addWithNewId(message1)
        chat.modifyById(id, message2)
        Assertions.assertEquals(message2, chat.getById(id))
    }

    @Test
    fun testDelete() {
        val chat: ChatMessageDao by inject()
        val id1 = chat.addWithNewId(message1)
        val id2 = chat.addWithNewId(message2)
        chat.deleteById(id1)
        Assertions.assertEquals(null, chat.getById(id1))
        Assertions.assertEquals(message2, chat.getById(id2))
        Assertions.assertEquals(1, chat.size)
    }

    @Test
    fun testSearchByText() {
        val chat: ChatMessageDao by inject()
        val id1 = chat.addWithNewId(message1)
        val id2 = chat.addWithNewId(message2)
        Assertions.assertEquals(chat.searchByText("Alya is cool version 2"), listOf(id2))
        Assertions.assertEquals(chat.searchByText("Alya is cool"), listOf(id1, id2))
        chat.deleteById(id2)
        Assertions.assertEquals(chat.searchByText("Alya"), listOf(id1))
    }
}
