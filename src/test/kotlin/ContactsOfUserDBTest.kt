import dao.ContactsOfUserDao
import dao.UserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class ContactsOfUserDBTest : DBTest() {
    @Test
    fun addAndContainsTest() {
        val base: ContactsOfUserDao by inject()
        val users: UserDao by inject()

        val alya = users.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        ).id.value

        val nikita = users.addNewUser(
            "Nikita",
            "Nikita@gmail.com",
            "5553535",
            "Nikita",
            "55555"
        ).id.value

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        ).id.value

        Assertions.assertTrue(base.add(alya, Pair(nikita, "Nikita")))
        Assertions.assertTrue(base.add(alya, Pair(antoha, "Kartoha")))
        Assertions.assertTrue(base.add(antoha, Pair(alya, "kek")))

        Assertions.assertTrue(base.contains(alya, Pair(nikita, "Nikita")))
        Assertions.assertTrue(base.contains(alya, Pair(antoha, "Kartoha")))
        Assertions.assertTrue(base.contains(antoha, Pair(alya, "kek")))

        Assertions.assertTrue(base.contains(alya, Pair(nikita, "Bosov")))
        Assertions.assertTrue(base.contains(alya, Pair(antoha, "KOKOKOKOKOKOKOKOKO")))

        Assertions.assertFalse(base.contains(nikita, Pair(alya, "kek")))
    }

    @Test
    fun removeAndSelectTest() {
        val base: ContactsOfUserDao by inject()
        val users: UserDao by inject()

        val alya = users.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        ).id.value

        val nikita = users.addNewUser(
            "Nikita",
            "Nikita@gmail.com",
            "5553535",
            "Nikita",
            "55555"
        ).id.value

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        ).id.value

        Assertions.assertTrue(base.add(alya, Pair(nikita, "Nikita")))
        Assertions.assertTrue(base.add(alya, Pair(antoha, "Kartoha")))
        Assertions.assertTrue(base.add(antoha, Pair(alya, "kek")))

        Assertions.assertEquals(2, base.select(alya).size)
        Assertions.assertTrue(base.select(alya).any{it.first == antoha})
        Assertions.assertTrue(base.select(alya).any{it.first == nikita})

        Assertions.assertEquals(1, base.select(antoha).size)
        Assertions.assertTrue(base.select(antoha).any{it.first == alya})

        Assertions.assertEquals(0, base.select(nikita).size)


        Assertions.assertTrue(base.contains(alya, Pair(nikita, "Nikita")))
        Assertions.assertTrue(base.contains(alya, Pair(antoha, "Kartoha")))

        Assertions.assertTrue(base.remove(alya, Pair(nikita, "Nikita")))
        Assertions.assertTrue(base.remove(alya, Pair(antoha, "KOKOKOKOKOKOKOKOKO")))

        Assertions.assertFalse(base.contains(alya, Pair(nikita, "Nikita")))
        Assertions.assertFalse(base.contains(alya, Pair(antoha, "Kartoha")))

        Assertions.assertEquals(0, base.select(alya).size)
        Assertions.assertEquals(1, base.select(antoha).size)
        Assertions.assertEquals(0, base.select(nikita).size)
    }

    @Test
    fun changeNameTest() {
        val base: ContactsOfUserDao by inject()
        val users: UserDao by inject()

        val alya = users.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        ).id.value

        val nikita = users.addNewUser(
            "Nikita",
            "Nikita@gmail.com",
            "5553535",
            "Nikita",
            "55555"
        ).id.value

        val antoha = users.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        ).id.value

        Assertions.assertTrue(base.add(alya, Pair(nikita, "Nikita")))
        Assertions.assertTrue(base.add(alya, Pair(antoha, "Kartoha")))
        Assertions.assertTrue(base.add(antoha, Pair(alya, "kek")))

        base.changeName(alya, antoha, "Antoha")
        base.changeName(antoha, alya, "Alya")

        Assertions.assertTrue(base.select(alya).any({it.second == "Antoha"}))
        Assertions.assertFalse(base.select(alya).any({it.second == "Kartoha"}))
        Assertions.assertTrue(base.select(antoha).any({it.second == "Alya"}))
        Assertions.assertFalse(base.select(antoha).any({it.second == "kek"}))
    }
}