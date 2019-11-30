import dao.PersonalChatDao
import dao.UserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class PersonalChatTests: DBTest {
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
        )

        val vanya = users.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        val chat1 = chats.addNewPersonalChat(alya.id.value, vanya.id.value)
        val chat2 = chats.addNewPersonalChat(239, 30)
        val chat3 = chats.addNewPersonalChat(alya.id.value, 239)

        Assertions.assertNotEquals(null, chat1)

        Assertions.assertEquals(null, chat2)
        Assertions.assertEquals(null, chat3)
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
        )

        val vanya = users.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        val chat1 = chats.addNewPersonalChat(alya.id.value, vanya.id.value)

        Assertions.assertNotEquals(null, chat1?.id?.value)
        if (chat1?.id?.value != null) {
            Assertions.assertEquals(chat1.id, chats.getById(chat1.id.value)?.id)
            Assertions.assertEquals(alya.id.value, chats.getById(chat1.id.value)?.member1?.value)
            Assertions.assertEquals(vanya.id.value, chats.getById(chat1.id.value)?.member2?.value)
        }

        Assertions.assertEquals(null, chats.getById(239))
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
        )

        val vanya = users.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        val chat1 = chats.addNewPersonalChat(alya.id.value, vanya.id.value)

        Assertions.assertNotEquals(null, chat1?.id?.value)
        if (chat1?.id?.value != null) {
            Assertions.assertEquals(chat1.id, chats.getById(chat1.id.value)?.id)

            chats.deleteById(chat1.id.value)

            Assertions.assertEquals(null, chats.getById(chat1.id.value)?.id)
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

        val chat1 = chats.addNewPersonalChat(nikita.id.value, vanya.id.value)
        val chat2 = chats.addNewPersonalChat(nikita.id.value, antoha.id.value)
        val chat3 = chats.addNewPersonalChat(antoha.id.value, vanya.id.value)

        Assertions.assertNotEquals(null, chat1?.id?.value)
        Assertions.assertNotEquals(null, chat2?.id?.value)
        Assertions.assertNotEquals(null, chat3?.id?.value)

        Assertions.assertEquals(3, chats.size)
        if (chat1?.id?.value != null)
            chats.deleteById(chat1.id.value)

        Assertions.assertEquals(2, chats.size)
        if (chat2?.id?.value != null)
            chats.deleteById(chat2.id.value)

        Assertions.assertEquals(1, chats.size)
        if (chat3?.id?.value != null)
            chats.deleteById(chat3.id.value)

        Assertions.assertEquals(0, chats.size)
    }
}