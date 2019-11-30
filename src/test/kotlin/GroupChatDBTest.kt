import dao.GroupChatDao
import dao.UserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class GroupChatDBTest: DBTest {
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
        )

        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "knskcsa")
        val chat2 = chats.addNewGroupChat(239,"null", null)

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
        )

        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "ajhbjakl")

        Assertions.assertNotNull( chat1?.id?.value)
        if (chat1?.id?.value != null) {
            Assertions.assertEquals(chat1.id, chats.getById(chat1.id.value)?.id)
            Assertions.assertEquals(alya.id.value, chats.getById(chat1.id.value)?.owner?.value)
            Assertions.assertEquals("Alin chat", chats.getById(chat1.id.value)?.chatName)
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
        )

        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "jaknaasx")

        Assertions.assertNotNull(chat1?.id?.value)
        if (chat1?.id?.value != null) {
            Assertions.assertEquals(chat1.id, chats.getById(chat1.id.value)?.id)

            chats.deleteById(chat1.id.value)

            Assertions.assertNull(chats.getById(chat1.id.value)?.id)
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
        )

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        )

        val chat1 = chats.addNewGroupChat(vanya.id.value, "Vanin chat", "oqlmcznka")
        val chat2 = chats.addNewGroupChat(antoha.id.value, "Antohin chat", "nvmbvhajekf")

        Assertions.assertNotNull(chat1?.id?.value)
        Assertions.assertNotNull(chat2?.id?.value)

        Assertions.assertEquals(2, chats.size)
        if (chat1?.id?.value != null)
            chats.deleteById(chat1.id.value)

        Assertions.assertEquals(1, chats.size)
        if (chat2?.id?.value != null)
            chats.deleteById(chat2.id.value)

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
        )

        val vanya = users.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "1")
        val chat2 = chats.addNewGroupChat(alya.id.value, "Alin chat", "2")
        val chat3 = chats.addNewGroupChat(vanya.id.value, "Vanin chat", "3")

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
        )

        val vanya = users.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "1")
        val chat2 = chats.addNewGroupChat(alya.id.value, "Alin chat", "2")
        val chat3 = chats.addNewGroupChat(vanya.id.value, "Vanin chat", "3")

        Assertions.assertEquals(chat1?.id?.value, chats.getChatByInviteLink("1")?.id?.value)
        Assertions.assertEquals(chat2?.id?.value, chats.getChatByInviteLink("2")?.id?.value)
        Assertions.assertEquals(chat3?.id?.value, chats.getChatByInviteLink("3")?.id?.value)
        Assertions.assertNull(chats.getChatByInviteLink("4"))
    }
}