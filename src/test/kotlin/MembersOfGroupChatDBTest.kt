import dao.GroupChatDao
import dao.GroupChatsOfUserDao
import dao.MembersOfGroupChatDao
import dao.UserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class MembersOfGroupChatDBTest : DBTest {
    @Test
    fun addTest() {
        val base: MembersOfGroupChatDao by inject()
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

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        ).id.value


        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "knskcsa")?.id?.value
        val chat2 = chats.addNewGroupChat(vanya, "Vanin chat", "oqlmcznka")?.id?.value

        Assertions.assertNotNull(chat1)
        Assertions.assertNotNull(chat2)

        if (chat1 != null) {
            Assertions.assertTrue(base.add(chat1, vanya))
            Assertions.assertTrue(base.add(chat1, antoha))
        }

        if (chat2 != null)
            Assertions.assertTrue(base.add(chat2, alya))

        Assertions.assertFalse(base.add(239, alya))

    }

    @Test
    fun removeTest() {
        val base: MembersOfGroupChatDao by inject()
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

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        ).id.value


        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "knskcsa")?.id?.value

        Assertions.assertNotNull(chat1)

        if (chat1 != null) {
            Assertions.assertTrue(base.add(chat1, vanya))
            Assertions.assertTrue(base.add(chat1, antoha))

            Assertions.assertTrue(base.remove(chat1, vanya))
            Assertions.assertFalse(base.remove(chat1, vanya))

            Assertions.assertFalse(base.remove(chat1, alya))
        }

        Assertions.assertFalse(base.remove(239, alya))
    }

    @Test
    fun selectTest() {
        val base: MembersOfGroupChatDao by inject()
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

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        ).id.value


        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "knskcsa")?.id?.value
        val chat2 = chats.addNewGroupChat(vanya, "Vanin chat", "oqlmcznka")?.id?.value

        Assertions.assertNotNull(chat1)
        Assertions.assertNotNull(chat2)

        if (chat1 != null)
            Assertions.assertTrue(base.add(chat1, vanya))
        if (chat1 != null)
            Assertions.assertTrue(base.add(chat1, antoha))
        if (chat2 != null)
            Assertions.assertTrue(base.add(chat2, alya))

        if (chat1 != null && chat2 != null) {
            Assertions.assertEquals(3, base.select(chat1).size)
            Assertions.assertEquals(2, base.select(chat2).size)

            Assertions.assertTrue(base.remove(chat1, antoha))

            Assertions.assertEquals(2, base.select(chat1).size)
        }

    }

    @Test
    fun containsTest() {
        val base: MembersOfGroupChatDao by inject()
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

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        ).id.value


        val chat1 = chats.addNewGroupChat(alya, "Alin chat", "knskcsa")?.id?.value
        val chat2 = chats.addNewGroupChat(vanya, "Vanin chat", "oqlmcznka")?.id?.value

        Assertions.assertNotNull(chat1)
        Assertions.assertNotNull(chat2)

        if (chat1 != null) {
            Assertions.assertTrue(base.add(chat1, vanya))
            Assertions.assertTrue(base.add(chat1, antoha))

            Assertions.assertTrue(base.contains(chat1, alya))
            Assertions.assertTrue(base.contains(chat1, vanya))
            Assertions.assertTrue(base.contains(chat1, antoha))

            Assertions.assertTrue(base.remove(chat1, vanya))
            Assertions.assertFalse(base.contains(chat1, vanya))

        }

        if (chat2 != null) {
            Assertions.assertTrue(base.add(chat2, alya))

            Assertions.assertTrue(base.contains(chat2, alya))
            Assertions.assertTrue(base.contains(chat2, vanya))

            Assertions.assertFalse(base.contains(chat2, antoha))
        }

    }
}