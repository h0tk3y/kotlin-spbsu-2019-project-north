import model.Message
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class MessageDbTest : DBTest {
    @Test
    fun basicTest() {
        val base: MessageDao by inject()
        val msg = Message(
            0, 0, 0, "My name is Sasha",
            0, false, false
        )
        val id = base.addWithNewId(msg)
        Assertions.assertEquals(msg, base.getById(id))
    }
}