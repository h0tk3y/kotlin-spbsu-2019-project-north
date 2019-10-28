import dao.MessageDao
import dao.ChatDao
import model.GroupChat
import model.PersonalChat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class ChatDbTest : DBTest {
    @Test
    fun addWithNewIdAndGetByIdTest() {
        val base: ChatDao by inject()
        val chat = PersonalChat(1, 2)
        val id = base.addWithNewId(chat)
        Assertions.assertEquals(chat, base.getById(id))
        Assertions.assertEquals(null, base.getById(id + 1))
    }

    @Test
    fun deleteByIdTest() {
        val base: ChatDao by inject()
        val chat = PersonalChat(1, 2)
        val id = base.addWithNewId(chat)
        Assertions.assertEquals(chat, base.getById(id))
        base.deleteById(id + 1)
        Assertions.assertEquals(chat, base.getById(id))
        base.deleteById(id)
        Assertions.assertEquals(null, base.getById(id))
    }

    @Test
    fun sizeTest() {
        val base: ChatDao by inject()
        Assertions.assertEquals(0, base.size)

        val chat1 = PersonalChat(1, 2)
        val id1 = base.addWithNewId(chat1)
        Assertions.assertEquals(1, base.size)

        val messageBase: MessageDao by inject()

        val chat2 = GroupChat(
            messageBase,
            1,
            "Kotlin project team North",
            "fgvsdfeg"
        )
        val id2 = base.addWithNewId(chat2)
        Assertions.assertEquals(2, base.size)

        Assertions.assertEquals(2, base.size)
        base.deleteById(id1)
        Assertions.assertEquals(1, base.size)
        base.deleteById(id1)
        Assertions.assertEquals(1, base.size)
        base.deleteById(id2)
        Assertions.assertEquals(0, base.size)
    }

    @Test
    fun getChatByInviteLinkTest() {
        val base: ChatDao by inject()
        Assertions.assertEquals(0, base.size)

        val chat1 = PersonalChat(1, 2)
        val id1 = base.addWithNewId(chat1)

        val messageBase: MessageDao by inject()

        val chat2 = GroupChat(
            messageBase,
            1,
            "Kotlin project team North",
            "pass"
        )
        val id2 = base.addWithNewId(chat2)

        val chat3 = GroupChat(
            messageBase,
            1,
            "Kotlin project team North",
            "ketyz"
        )
        val id3 = base.addWithNewId(chat3)

        Assertions.assertEquals(id2, base.getChatByInviteLink("pass"))
        Assertions.assertEquals(id3, base.getChatByInviteLink("ketyz"))
        Assertions.assertEquals(null, base.getChatByInviteLink("metupo"))
    }

    @Test
    fun searchByNameTest() {
        val base: ChatDao by inject()
        Assertions.assertEquals(0, base.size)

        val chat1 = PersonalChat(1, 2)
        val id1 = base.addWithNewId(chat1)

        val messageBase: MessageDao by inject()

        val chat2 = GroupChat(
            messageBase,
            1,
            "Kotlin project team North",
            "pass"
        )
        val id2 = base.addWithNewId(chat2)

        val chat3 = GroupChat(
            messageBase,
            1,
            "Kotlin project team North",
            "ketyz"
        )
        val id3 = base.addWithNewId(chat3)

        val chat4 = GroupChat(
            messageBase,
            1,
            "Kotlin project team South",
            "ketyz"
        )
        val id4 = base.addWithNewId(chat4)

        Assertions.assertEquals(listOf(id2, id3), base.searchByName("Kotlin project team North"))
        Assertions.assertEquals(listOf(id4), base.searchByName("Kotlin project team South"))
        Assertions.assertEquals(listOf<Long>(), base.searchByName("Kotlin project team West"))
    }
}