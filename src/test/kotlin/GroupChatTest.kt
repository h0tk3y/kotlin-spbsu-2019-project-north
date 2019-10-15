import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class GroupChatTest : KoinTest {
    val base : MessageDao by inject()

    @Test
    fun testAddUserPublicChat() {
        val chat = GroupChat(base, 0, "kek", null)
        Assertions.assertEquals(1, chat.countUsers)
        Assertions.assertTrue(chat.addUser(1, null))
        Assertions.assertEquals(2, chat.countUsers)
        Assertions.assertFalse(chat.addUser(1, null))
        Assertions.assertEquals(2, chat.countUsers)
    }

    @Test
    fun testAddUserPrivateChat() {
        val chat = GroupChat(base, 0, "lol", "sm.me/lolabacabadaba")
        Assertions.assertEquals(1, chat.countUsers)
        Assertions.assertFalse(chat.addUser(1, null))
        Assertions.assertEquals(1, chat.countUsers)
        Assertions.assertTrue(chat.addUser(2, "sm.me/lolabacabadaba"))
        Assertions.assertEquals(2, chat.countUsers)
        Assertions.assertFalse(chat.addUser(3, "t.me/lolabacabadaba"))
        Assertions.assertEquals(2, chat.countUsers)
        Assertions.assertFalse(chat.addUser(2, "sm.me/lolabacabadaba"))
        Assertions.assertEquals(2, chat.countUsers)
    }
    
    @Test
    fun testLeave() {
        val chat = GroupChat(base, 0, "kek", null)
        chat.addUser(1)
        Assertions.assertEquals(2, chat.countUsers)
        chat.leave(1)
        Assertions.assertEquals(1, chat.countUsers)
    }
}

