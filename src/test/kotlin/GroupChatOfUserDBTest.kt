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
        )

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


        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "knskcsa")
        val chat2 = chats.addNewGroupChat(vanya.id.value, "Vanin chat", "oqlmcznka")

        if (chat1?.id?.value != null) {
            Assertions.assertTrue(base.add(vanya.id.value, chat1.id.value))
            Assertions.assertTrue(base.add(antoha.id.value, chat1.id.value))
        }

        if (chat2?.id?.value != null)
            Assertions.assertTrue(base.add(alya.id.value, chat2.id.value))

        Assertions.assertFalse(base.add(alya.id.value, 239))

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
        )

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


        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "knskcsa")

        if (chat1?.id?.value != null) {
            Assertions.assertTrue(base.add(vanya.id.value, chat1.id.value))
            Assertions.assertTrue(base.add(antoha.id.value, chat1.id.value))

            Assertions.assertTrue(base.remove(vanya.id.value, chat1.id.value))
            Assertions.assertFalse(base.remove(vanya.id.value, chat1.id.value))
        }

        Assertions.assertFalse(base.remove(alya.id.value, 239))
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
        )

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


        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "knskcsa")
        val chat2 = chats.addNewGroupChat(vanya.id.value, "Vanin chat", "oqlmcznka")

        if (chat1?.id?.value != null)
            Assertions.assertTrue(base.add(vanya.id.value, chat1.id.value))
        if (chat1?.id?.value != null)
            Assertions.assertTrue(base.add(antoha.id.value, chat1.id.value))
        if (chat2?.id?.value != null)
            Assertions.assertTrue(base.add(alya.id.value, chat2.id.value))

        Assertions.assertFalse(base.add(alya.id.value, 239))
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
        )

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


        val chat1 = chats.addNewGroupChat(alya.id.value, "Alin chat", "knskcsa")
        val chat2 = chats.addNewGroupChat(vanya.id.value, "Vanin chat", "oqlmcznka")

        if (chat1?.id?.value != null)
            Assertions.assertTrue(base.add(vanya.id.value, chat1.id.value))
        if (chat1?.id?.value != null)
            Assertions.assertTrue(base.add(antoha.id.value, chat1.id.value))
        if (chat2?.id?.value != null)
            Assertions.assertTrue(base.add(alya.id.value, chat2.id.value))

        Assertions.assertFalse(base.add(alya.id.value, 239))
    }
}