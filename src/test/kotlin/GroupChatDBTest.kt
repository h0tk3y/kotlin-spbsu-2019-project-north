import dao.GroupChatDao
import dao.UserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class GroupChatDBTest : DBTest() {
    @Test
    fun addNewGroupChatTest() {
        val chats: GroupChatDao by inject()
        val users: UserDao by inject()

        val alya = users.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        ).id.value

        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "knskcsa")?.id?.value
        val chat2 = chats.addNewGroupChat(239,"null", null)?.id?.value

        Assertions.assertNotNull(chat1)

        Assertions.assertNull(chat2)
    }

    @Test
    fun getByIdTest() {
        val chats: GroupChatDao by inject()
        val users: UserDao by inject()

        val alya = users.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        ).id.value

        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "ajhbjakl")?.id?.value

        Assertions.assertNotNull( chat1)
        if (chat1 != null) {
            Assertions.assertEquals(chat1, chats.getById(chat1)?.id?.value)
            Assertions.assertEquals(alya, chats.getById(chat1)?.owner?.value)
            Assertions.assertEquals("Alin chat", chats.getById(chat1)?.chatName)
        }

        Assertions.assertNull(chats.getById(239))
    }

    @Test
    fun deleteByIdTest() {
        val chats: GroupChatDao by inject()
        val users: UserDao by inject()

        val alya = users.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        ).id.value

        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "jaknaasx")?.id?.value

        Assertions.assertNotNull(chat1)
        if (chat1 != null) {
            Assertions.assertEquals(chat1, chats.getById(chat1)?.id?.value)

            chats.deleteById(chat1)

            Assertions.assertNull(chats.getById(chat1))
        }
    }

    @Test
    fun sizeTest() {
        val chats: GroupChatDao by inject()
        val users: UserDao by inject()

        val vanya = users.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        ).id.value

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        ).id.value

        val chat1 = chats.addNewGroupChat(vanya, "Vanin chat", "oqlmcznka")?.id?.value
        val chat2 = chats.addNewGroupChat(antoha, "Antohin chat", "nvmbvhajekf")?.id?.value

        Assertions.assertNotNull(chat1)
        Assertions.assertNotNull(chat2)

        Assertions.assertEquals(2, chats.size)
        if (chat1 != null)
            chats.deleteById(chat1)

        Assertions.assertEquals(1, chats.size)
        if (chat2 != null)
            chats.deleteById(chat2)

        Assertions.assertEquals(0, chats.size)
    }

    @Test
    fun searchByNameTest() {
        val chats: GroupChatDao by inject()
        val users: UserDao by inject()

        val alya = users.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        ).id.value

        val vanya = users.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        ).id.value

        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "1")?.id?.value
        val chat2 = chats.addNewGroupChat(alya, "Alin chat", "2")?.id?.value
        val chat3 = chats.addNewGroupChat(vanya, "Vanin chat", "3")?.id?.value

        Assertions.assertEquals(2, chats.searchByName("Alin chat").size)
        Assertions.assertTrue(chats.searchByName("Alin chat").any{it.uniqueLink == "1"})
        Assertions.assertTrue(chats.searchByName("Alin chat").any{it.uniqueLink == "2"})

        Assertions.assertEquals(1, chats.searchByName("Vanin chat").size)
        Assertions.assertTrue(chats.searchByName("Vanin chat").any{it.uniqueLink == "3"})

        Assertions.assertEquals(0, chats.searchByName("Nety(((").size)
    }

    @Test
    fun getChatByInviteLinkTest() {
        val chats: GroupChatDao by inject()
        val users: UserDao by inject()

        val alya = users.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        ).id.value

        val vanya = users.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        ).id.value

        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "1")?.id?.value
        val chat2 = chats.addNewGroupChat(alya, "Alin chat", "2")?.id?.value
        val chat3 = chats.addNewGroupChat(vanya, "Vanin chat", "3")?.id?.value

        Assertions.assertEquals(chat1, chats.getChatByInviteLink("1")?.id?.value)
        Assertions.assertEquals(chat2, chats.getChatByInviteLink("2")?.id?.value)
        Assertions.assertEquals(chat3, chats.getChatByInviteLink("3")?.id?.value)
        Assertions.assertNull(chats.getChatByInviteLink("4")?.id?.value)
    }
}