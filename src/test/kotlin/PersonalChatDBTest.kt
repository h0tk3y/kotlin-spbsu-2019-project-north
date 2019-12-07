import dao.PersonalChatDao
import dao.UserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class PersonalChatTests : DBTest() {
    @Test
    fun addNewPersonalChatTest() {
        val chats: PersonalChatDao by inject()
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

        val chat1 = chats.addNewPersonalChat(alya, vanya)?.id?.value
        val chat2 = chats.addNewPersonalChat(239, 30)?.id?.value
        val chat3 = chats.addNewPersonalChat(alya, 239)?.id?.value

        Assertions.assertNotNull(chat1)

        Assertions.assertNull(chat2)
        Assertions.assertNull(chat3)
    }

    @Test
    fun getByIdTest() {
        val chats: PersonalChatDao by inject()
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

        val chat1 = chats.addNewPersonalChat(alya, vanya)?.id?.value

        Assertions.assertNotNull( chat1)
        if (chat1 != null) {
            Assertions.assertEquals(chat1, chats.getById(chat1)?.id?.value)
            Assertions.assertEquals(alya, chats.getById(chat1)?.member1?.value)
            Assertions.assertEquals(vanya, chats.getById(chat1)?.member2?.value)
        }

        Assertions.assertNull(chats.getById(239))
    }

    @Test
    fun deleteByIdTest() {
        val chats: PersonalChatDao by inject()
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

        val chat1 = chats.addNewPersonalChat(alya, vanya)?.id?.value

        Assertions.assertNotNull(chat1)
        if (chat1 != null) {
            Assertions.assertEquals(chat1, chats.getById(chat1)?.id?.value)

            chats.deleteById(chat1)

            Assertions.assertNull(chats.getById(chat1)?.id)
        }

    }

    @Test
    fun sizeTest() {
        val chats: PersonalChatDao by inject()
        val users: UserDao by inject()

        val nikita = users.addNewUser(
            "Nikita",
            "Nikita@gmail.com",
            "5553535",
            "Nikita",
            "55555"
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

        val chat1 = chats.addNewPersonalChat(nikita, vanya)?.id?.value
        val chat2 = chats.addNewPersonalChat(nikita, antoha)?.id?.value
        val chat3 = chats.addNewPersonalChat(antoha, vanya)?.id?.value

        Assertions.assertNotNull(chat1)
        Assertions.assertNotNull(chat2)
        Assertions.assertNotNull(chat3)

        Assertions.assertEquals(3, chats.size)
        if (chat1 != null)
            chats.deleteById(chat1)

        Assertions.assertEquals(2, chats.size)
        if (chat2 != null)
            chats.deleteById(chat2)

        Assertions.assertEquals(1, chats.size)
        if (chat3 != null)
            chats.deleteById(chat3)

        Assertions.assertEquals(0, chats.size)
    }
}