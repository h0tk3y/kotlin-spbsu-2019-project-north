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

        Assertions.assertNotNull(chat1?.id?.value)
        Assertions.assertNotNull(chat2?.id?.value)

        if (chat1?.id?.value != null) {
            Assertions.assertTrue(base.add(chat1.id.value, vanya.id.value))
            Assertions.assertTrue(base.add(chat1.id.value, antoha.id.value))
        }

        if (chat2?.id?.value != null)
            Assertions.assertTrue(base.add(chat2.id.value, alya.id.value))

        Assertions.assertFalse(base.add(239, alya.id.value))

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

        Assertions.assertNotNull(chat1?.id?.value)

        if (chat1?.id?.value != null) {
            Assertions.assertTrue(base.add(chat1.id.value, vanya.id.value))
            Assertions.assertTrue(base.add(chat1.id.value, antoha.id.value))

            Assertions.assertTrue(base.remove(chat1.id.value, vanya.id.value))
            Assertions.assertFalse(base.remove(chat1.id.value, vanya.id.value))

            Assertions.assertFalse(base.remove(chat1.id.value, alya.id.value))
        }

        Assertions.assertFalse(base.remove(239, alya.id.value))
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

        Assertions.assertNotNull(chat1?.id?.value)
        Assertions.assertNotNull(chat2?.id?.value)

        if (chat1?.id?.value != null)
            Assertions.assertTrue(base.add(chat1.id.value, vanya.id.value))
        if (chat1?.id?.value != null)
            Assertions.assertTrue(base.add(chat1.id.value, antoha.id.value))
        if (chat2?.id?.value != null)
            Assertions.assertTrue(base.add(chat2.id.value, alya.id.value))

        if (chat1?.id?.value != null && chat2?.id?.value != null) {
            Assertions.assertEquals(3, base.select(chat1.id.value).size)
            Assertions.assertEquals(2, base.select(chat2.id.value).size)

            Assertions.assertTrue(base.remove(chat1.id.value, antoha.id.value))

            Assertions.assertEquals(2, base.select(chat1.id.value).size)
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

        Assertions.assertNotNull(chat1?.id?.value)
        Assertions.assertNotNull(chat2?.id?.value)

        if (chat1?.id?.value != null) {
            Assertions.assertTrue(base.add(chat1.id.value, vanya.id.value))
            Assertions.assertTrue(base.add(chat1.id.value, antoha.id.value))

            Assertions.assertTrue(base.contains(chat1.id.value, alya.id.value))
            Assertions.assertTrue(base.contains(chat1.id.value, vanya.id.value))
            Assertions.assertTrue(base.contains(chat1.id.value, antoha.id.value))

            Assertions.assertTrue(base.remove(chat1.id.value, vanya.id.value))
            Assertions.assertFalse(base.contains(chat1.id.value, vanya.id.value))

        }

        if (chat2?.id?.value != null) {
            Assertions.assertTrue(base.add(chat2.id.value, alya.id.value))

            Assertions.assertTrue(base.contains(chat2.id.value, alya.id.value))
            Assertions.assertTrue(base.contains(chat2.id.value, vanya.id.value))

            Assertions.assertFalse(base.contains(chat2.id.value, antoha.id.value))
        }

    }
}