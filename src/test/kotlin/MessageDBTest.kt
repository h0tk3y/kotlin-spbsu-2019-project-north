import dao.GroupChatDao
import dao.MessageDao
import dao.PersonalChatDao
import dao.UserDao
import model.Message
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class MessageDBTest : DBTest() {
    @Test
    fun getByIdTest() {
        val base: MessageDao by inject()
        val pchats: PersonalChatDao by inject()
        val gchats: GroupChatDao by inject()
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

        val chat1 = pchats.addNewPersonalChat(alya, vanya)
        val chat2 = gchats.addNewGroupChat(alya, "Alin chat", "1")

        var msg1 : Message? = null
        if (chat1?.id?.value != null)
            msg1 = base.addNewMessage(alya, true, chat1.id.value, "Priv")
        var msg2 : Message? = null
        if (chat2?.id?.value != null)
            msg2 = base.addNewMessage(alya, false, chat2.id.value, "Vsem privet!")

        Assertions.assertNotNull(msg1?.id?.value)
        if (msg1?.id?.value != null && chat1?.id?.value != null) {
            Assertions.assertEquals(msg1.id.value, base.getById(msg1.id.value)?.id?.value)
            Assertions.assertEquals(chat1.id.value, msg1.chat)
            Assertions.assertTrue(msg1.isPersonal)
        }

        Assertions.assertNotNull(msg2?.id?.value)
        if (msg2?.id?.value != null && chat2?.id?.value != null) {
            Assertions.assertEquals(msg2.id.value, base.getById(msg2.id.value)?.id?.value)
            Assertions.assertEquals(chat2.id.value, msg2.chat)
            Assertions.assertFalse(msg2.isPersonal)
        }
    }

    @Test
    fun deleteByIdTest() {
        val base: MessageDao by inject()
        val pchats: PersonalChatDao by inject()
        val gchats: GroupChatDao by inject()
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

        val chat1 = pchats.addNewPersonalChat(alya, vanya)
        val chat2 = gchats.addNewGroupChat(alya, "Alin chat", "1")

        var msg1 : Message? = null
        if (chat1?.id?.value != null)
            msg1 = base.addNewMessage(alya, true, chat1.id.value, "Priv")
        var msg2 : Message? = null
        if (chat2?.id?.value != null)
            msg2 = base.addNewMessage(alya, false, chat2.id.value, "Vsem privet!")

        Assertions.assertNotNull(msg1?.id?.value)
        if (msg1?.id?.value != null && chat1?.id?.value != null) {
            Assertions.assertEquals(msg1.id.value, base.getById(msg1.id.value)?.id?.value)

            base.deleteById(msg1.id.value)

            Assertions.assertNull(base.getById(msg1.id.value))
        }

        Assertions.assertNotNull(msg2?.id?.value)
        if (msg2?.id?.value != null && chat2?.id?.value != null) {
            Assertions.assertEquals(msg2.id.value, base.getById(msg2.id.value)?.id?.value)

            base.deleteById(msg2.id.value)

            Assertions.assertNull(base.getById(msg2.id.value))
        }
    }

    @Test
    fun findByUserAndfindSliceFromChatAndSizeTest() {
        val base: MessageDao by inject()
        val pchats: PersonalChatDao by inject()
        val gchats: GroupChatDao by inject()
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

        val chat1 = pchats.addNewPersonalChat(alya, vanya)
        val chat2 = gchats.addNewGroupChat(alya, "Alin chat", "1")

        var msg1 : Message? = null
        if (chat1?.id?.value != null)
            msg1 = base.addNewMessage(alya, true, chat1.id.value, "Priv")

        Assertions.assertEquals(1, base.size)

        var msg2 : Message? = null
        var msg3 : Message? = null
        var msg4 : Message? = null
        if (chat2?.id?.value != null) {
            msg2 = base.addNewMessage(alya, false, chat2.id.value, "Vsem privet!")

            Assertions.assertEquals(2, base.size)

            msg3 = base.addNewMessage(alya, false, chat2.id.value, "Alya is cool!")
            msg4 = base.addNewMessage(alya, false, chat2.id.value, "Kotlin top!")
        }

        Assertions.assertEquals(4, base.size)

        Assertions.assertEquals(4, base.findByUser(alya).size)
        Assertions.assertEquals(0, base.findByUser(vanya).size)

//        if (chat1?.id?.value != null) {
//            Assertions.assertEquals(0, base.findSliceFromChat(true, chat1.id.value, 0, 1).size)
//            Assertions.assertEquals(1, base.findSliceFromChat(true, chat1.id.value, 1, 1).size)
//        }
//
//        if (chat2?.id?.value != null) {
//            Assertions.assertEquals(3, base.findSliceFromChat(false, chat2.id.value, 3, 0).size)
//            Assertions.assertEquals(2, base.findSliceFromChat(false, chat2.id.value, 2, 1).size)
//        }
    }

}