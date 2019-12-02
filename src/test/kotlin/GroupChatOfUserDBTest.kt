import dao.GroupChatDao
import dao.GroupChatsOfUserDao
import dao.UserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class GroupChatOfUserDBTest : DBTest {
    @Test
    fun addTest() {
        val base: GroupChatsOfUserDao by inject()
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
            Assertions.assertTrue(base.add(vanya, chat1))
            Assertions.assertTrue(base.add(antoha, chat1))
        }

        if (chat2 != null)
            Assertions.assertTrue(base.add(alya, chat2))

        Assertions.assertFalse(base.add(alya, 239))

    }

    @Test
    fun removeTest() {
        val base: GroupChatsOfUserDao by inject()
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
            Assertions.assertTrue(base.add(vanya, chat1))
            Assertions.assertTrue(base.add(antoha, chat1))

            Assertions.assertTrue(base.remove(vanya, chat1))
            Assertions.assertFalse(base.remove(vanya, chat1))

            Assertions.assertFalse(base.remove(alya, chat1))
        }

        Assertions.assertFalse(base.remove(alya, 239))
    }

    @Test
    fun selectTest() {
        val base: GroupChatsOfUserDao by inject()
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
            Assertions.assertTrue(base.add(vanya, chat1))
        if (chat1 != null)
            Assertions.assertTrue(base.add(antoha, chat1))
        if (chat2 != null)
            Assertions.assertTrue(base.add(alya, chat2))

        Assertions.assertEquals(2, base.select(alya).size)
        Assertions.assertEquals(2, base.select(vanya).size)
        Assertions.assertEquals(1, base.select(antoha).size)

        if (chat1 != null)
            Assertions.assertTrue(base.remove(antoha, chat1))

        Assertions.assertEquals(0, base.select(antoha).size)

    }

    @Test
    fun containsTest() {
        val base: GroupChatsOfUserDao by inject()
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
            Assertions.assertTrue(base.add(vanya, chat1))
            Assertions.assertTrue(base.add(antoha, chat1))

            Assertions.assertTrue(base.contains(alya, chat1))
            Assertions.assertTrue(base.contains(vanya, chat1))
            Assertions.assertTrue(base.contains(antoha, chat1))

            Assertions.assertTrue(base.remove(vanya, chat1))
            Assertions.assertFalse(base.contains(vanya, chat1))

        }

        if (chat2 != null) {
            Assertions.assertTrue(base.add(alya, chat2))

            Assertions.assertTrue(base.contains(alya, chat2))
            Assertions.assertTrue(base.contains(vanya, chat2))

            Assertions.assertFalse(base.contains(antoha, chat2))
        }

    }
}