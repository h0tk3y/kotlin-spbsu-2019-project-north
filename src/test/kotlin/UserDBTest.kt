import dao.UserDao
import io.ktor.auth.UserPasswordCredential
import model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class UserDbTest : DBTest() {

    @Test
    fun getUserByCredentialsTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        val alya2 = base.addNewUser(
            "Alya",
            "Alya2@gmail.com",
            "7654321",
            "Bober",
            "123"
        )

        Assertions.assertEquals(null, base.getUserByCredentials(UserPasswordCredential("kek", "lol")))

        Assertions.assertEquals(alya.id, base.getUserByCredentials(UserPasswordCredential(alya.login, alya.password))?.id)
        Assertions.assertEquals(alya2.id, base.getUserByCredentials(UserPasswordCredential(alya2.login, alya2.password))?.id)

    }

    @Test
    fun getByIdTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        val antoha = base.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        )

        Assertions.assertEquals(alya.id, base.getById(alya.id.value)?.id)
        Assertions.assertEquals(alya.login, base.getById(alya.id.value)?.login)

        Assertions.assertEquals(antoha.id, base.getById(antoha.id.value)?.id)
        Assertions.assertEquals(antoha.password, base.getById(antoha.id.value)?.password)

        Assertions.assertNull(base.getById(239))

    }

    @Test
    fun deleteByIdTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        val alya2 = base.addNewUser(
            "Alya",
            "Alya2@gmail.com",
            "7654321",
            "Bober",
            "123"
        )

        Assertions.assertEquals(alya2.id, base.getUserByCredentials(UserPasswordCredential(alya2.login, alya2.password))?.id)

        base.deleteById(alya2.id.value)
        base.deleteById(239)

        Assertions.assertNull( base.getUserByCredentials(UserPasswordCredential(alya2.login, alya2.password))?.id)
        Assertions.assertNull( base.getUserByCredentials(UserPasswordCredential("kek", "lol")))
        Assertions.assertEquals(alya.id, base.getUserByCredentials(UserPasswordCredential(alya.login, alya.password))?.id)

    }

    @Test
    fun sizeTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        val nikita = base.addNewUser(
            "Nikita",
            "Nikita@gmail.com",
            "5553535",
            "Nikita",
            "55555"
        )

        Assertions.assertEquals(2, base.size)

        base.deleteById(alya.id.value)

        Assertions.assertEquals(1, base.size)

        base.deleteById(nikita.id.value)
        base.deleteById(239)

        Assertions.assertEquals(0, base.size)

    }

    @Test
    fun searchByNameTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        val alya2 = base.addNewUser(
            "Alya",
            "Alya2@gmail.com",
            "7654321",
            "Bober",
            "123"
        )

        val vanya = base.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        val alyas: List<User> = base.searchByName("Alya")
        val vanyas: List<User> = base.searchByName("Vanya")

        Assertions.assertEquals(2, alyas.size)
        Assertions.assertTrue(alyas.any{it.id == alya.id})
        Assertions.assertTrue(alyas.any{it.id == alya2.id})
        Assertions.assertEquals(1, vanyas.size)
        Assertions.assertEquals(vanya.id, vanyas[0].id)

    }

    @Test
    fun getByEmailTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        Assertions.assertEquals(alya.id, base.getByEmail("Alya@gmail.com")?.id)
        Assertions.assertNull( base.getByEmail("kek@gmail.com"))

    }

    @Test
    fun getByPhoneNumberTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        Assertions.assertEquals(alya.id, base.getByPhoneNumber("1234567")?.id)
        Assertions.assertNull(base.getByPhoneNumber("7654321"))

    }

    @Test
    fun updateNameTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Sasha",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        val vanya = base.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        base.updateName(vanya.id.value, "Sasha")
        Assertions.assertEquals("Sasha", base.getById(vanya.id.value)?.name)

        base.updateName(alya.id.value, "Alya")
        base.updateName(vanya.id.value, "Vanya")

        Assertions.assertEquals("Alya", base.getById(alya.id.value)?.name)
        Assertions.assertEquals("Vanya", base.getById(vanya.id.value)?.name)

    }

    @Test
    fun updateEmailTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        val vanya = base.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        val antoha = base.addNewUser(
            "Antoha",
            "Antoha@gmail.com",
            "4444444",
            "Kartoha",
            "4444"
        )

        base.updateEmail(antoha.id.value, "Alya@gmail.com")
        base.updateEmail(alya.id.value, "kek@gmail.com")
        base.updateEmail(vanya.id.value, "Alya@gmail.com")

        Assertions.assertEquals("kek@gmail.com", base.getById(alya.id.value)?.email)
        Assertions.assertEquals("Alya@gmail.com", base.getById(vanya.id.value)?.email)
        Assertions.assertEquals("Antoha@gmail.com", base.getById(antoha.id.value)?.email)

    }

    @Test
    fun existsLoginTest() {

        val base: UserDao by inject()

        val alya = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )

        val vanya = base.addNewUser(
            "Vanya",
            "Vanya@gmail.com",
            "8888888",
            "olivva",
            "8"
        )

        Assertions.assertTrue(base.existsLogin("Pingwin"))
        Assertions.assertTrue(base.existsLogin("olivva"))
        Assertions.assertFalse(base.existsLogin("Bober"))


    }
}
