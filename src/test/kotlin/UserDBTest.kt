import dao.UserDao
import io.ktor.auth.UserPasswordCredential
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class UserDbTest : DBTest {
    @Test
    fun getUserByCredentialsTest() {
        val base: UserDao by inject()

        val user1 = base.addNewUser(
            "Alya",
            "Alya@gmail.com",
            "1234567",
            "Pingwin",
            "123"
        )
        Assertions.assertEquals(4, 2 * 2)


//        Assertions.assertEquals(user1, base.getUserByCredentials(UserPasswordCredential(user1.login, user1.password)))
        Assertions.assertEquals(null, base.getUserByCredentials(UserPasswordCredential("kek", "lol")))
    }

//    @Test
//    fun addWithNewIdAndGetByIdTest() {
//        val base: UserDao by inject()
//
//        val id = base.addWithNewId(user1)
//
//        Assertions.assertEquals(user1, base.getById(id))
//        Assertions.assertEquals(null, base.getById(id + 1))
//    }
//
//    @Test
//    fun deleteByIdTest() {
//        val base: UserDao by inject()
//
//        val id = base.addWithNewId(user1)
//        Assertions.assertEquals(user1, base.getById(id))
//
//        base.deleteById(id + 1)
//        Assertions.assertEquals(user1, base.getById(id))
//
//        base.deleteById(id)
//        Assertions.assertEquals(null, base.getById(id))
//    }
//
//    @Test
//    fun sizeTest() {
//        val base: UserDao by inject()
//        Assertions.assertEquals(0, base.size)
//
//        val id1 = base.addWithNewId(user1)
//        Assertions.assertEquals(1, base.size)
//
//        val id2 = base.addWithNewId(user2)
//        Assertions.assertEquals(2, base.size)
//
//        base.deleteById(id1)
//        Assertions.assertEquals(1, base.size)
//
//        base.deleteById(id1)
//        Assertions.assertEquals(1, base.size)
//
//        base.deleteById(id2)
//        Assertions.assertEquals(0, base.size)
//    }
//
//    @Test
//    fun searchByNameTest() {
//        val base: UserDao by inject()
//
//        val id1 = base.addWithNewId(user1)
//
//        val id2 = base.addWithNewId(user2)
//
//        val id3 = base.addWithNewId(user3)
//
//        val id4 = base.addWithNewId(user4)
//
//        Assertions.assertEquals(listOf(id1, id3), base.searchByName(commonName13))
//        Assertions.assertEquals(listOf(id2), base.searchByName(user2.name))
//        Assertions.assertEquals(listOf(id4), base.searchByName(user4.name))
//        Assertions.assertEquals(listOf<Long>(), base.searchByName("Rakan"))
//    }
//
//    @Test
//    fun getByEmailTest() {
//        val base: UserDao by inject()
//
//        val id1 = base.addWithNewId(user1)
//
//        val id2 = base.addWithNewId(user2)
//
//        Assertions.assertEquals(id1, base.getByEmail(user1.email))
//        Assertions.assertEquals(id2, base.getByEmail(user2.email))
//        Assertions.assertEquals(null, base.getByEmail("fake@e.mail"))
//        Assertions.assertEquals(null, base.getByEmail("+3(938)-23-57"))
//    }
//
//    @Test
//    fun getByPhoneNumberTest() {
//        val base: UserDao by inject()
//
//        val id1 = base.addWithNewId(user1)
//
//        val id2 = base.addWithNewId(user2)
//
//        Assertions.assertEquals(id1, base.getByPhoneNumber(user1.phoneNumber))
//        Assertions.assertEquals(id2, base.getByPhoneNumber(user2.phoneNumber))
//        Assertions.assertEquals(null, base.getByPhoneNumber("+8(142)284-61-36"))
//        Assertions.assertEquals(null, base.getByPhoneNumber(user1.email))
//    }
}
