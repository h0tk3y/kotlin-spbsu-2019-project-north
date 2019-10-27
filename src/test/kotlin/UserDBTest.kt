import dao.UserDao
import model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class UserDbTest : DBTest {
    @Test
    fun addWithNewIdAndGetByIdTest() {
        val base: UserDao by inject()

        val user = User(
            123,
            "John",
            "john@smith.com",
            "+3(938)781-23-57",
            "JohnSmith",
            "qwerty"
        )
        val id = base.addWithNewId(user)

        Assertions.assertEquals(user, base.getById(id))
        Assertions.assertEquals(null, base.getById(id + 1))
    }

    @Test
    fun modifyByIdTest() {
        val base: UserDao by inject()

        val user1 = User(
            123,
            "John",
            "john@smith.com",
            "+3(938)781-23-57",
            "JohnSmith",
            "qwerty"
        )
        val id = base.addWithNewId(user1)
        Assertions.assertEquals(user1, base.getById(id))

        val user2 = User(
            124,
            "Joanna",
            "jonanna@clock.org",
            "+9(042)222-33-44",
            "JoannaClock",
            "123456"
        )
        base.modifyById(id, user2)
        Assertions.assertEquals(user2, base.getById(id))
    }

    @Test
    fun deleteByIdTest() {
        val base: UserDao by inject()

        val user = User(
            123,
            "John",
            "john@smith.com",
            "+3(938)781-23-57",
            "JohnSmith",
            "qwerty"
        )
        val id = base.addWithNewId(user)
        Assertions.assertEquals(user, base.getById(id))

        base.deleteById(id + 1)
        Assertions.assertEquals(user, base.getById(id))

        base.deleteById(id)
        Assertions.assertEquals(null, base.getById(id))
    }

    @Test
    fun sizeTest() {
        val base: UserDao by inject()
        Assertions.assertEquals(0, base.size)

        val user1 = User(
            123,
            "John",
            "john@smith.com",
            "+3(938)781-23-57",
            "JohnSmith",
            "qwerty"
        )
        val id1 = base.addWithNewId(user1)
        Assertions.assertEquals(1, base.size)

        val user2 = User(
            124,
            "Joanna",
            "jonanna@clock.org",
            "+9(042)222-33-44",
            "JoannaClock",
            "123456"
        )
        val id2 = base.addWithNewId(user2)
        Assertions.assertEquals(2, base.size)

        base.deleteById(id1)
        Assertions.assertEquals(1, base.size)

        base.deleteById(id1)
        Assertions.assertEquals(1, base.size)

        base.deleteById(id2)
        Assertions.assertEquals(0, base.size)
    }

    @Test
    fun searchByNameTest() {
        val base: UserDao by inject()

        val user1 = User(
            123,
            "John",
            "john@smith.com",
            "+3(938)781-23-57",
            "JohnSmith",
            "qwerty"
        )
        val id1 = base.addWithNewId(user1)

        val user2 = User(
            124,
            "Joanna",
            "jonanna@clock.org",
            "+9(042)222-33-44",
            "JoannaClock",
            "123456"
        )
        val id2 = base.addWithNewId(user2)

        val user3 = User(
            125,
            "John",
            "delim@state.uk",
            "+1(125)128-41-57",
            "Delimery",
            "gvqzkfcjjeksoerjvr"
        )
        val id3 = base.addWithNewId(user3)

        val user4 = User(
            126,
            "Nakit",
            "postre@gmte.io",
            "+2(826)392-25-37",
            "PeterNut",
            "sjlhergjjkvakar"
        )
        val id4 = base.addWithNewId(user4)

        Assertions.assertEquals(listOf(id1, id3), base.searchByName("John"))
        Assertions.assertEquals(listOf(id2), base.searchByName("Joanna"))
        Assertions.assertEquals(listOf(id4), base.searchByName("Nakit"))
        Assertions.assertEquals(listOf<Long>(), base.searchByName("Johny"))
    }

    @Test
    fun getByEmailTest() {
        val base: UserDao by inject()

        val user1 = User(
            123,
            "John",
            "john@smith.com",
            "+3(938)781-23-57",
            "JohnSmith",
            "qwerty"
        )
        val id1 = base.addWithNewId(user1)

        val user2 = User(
            124,
            "Joanna",
            "jonanna@clock.org",
            "+9(042)222-33-44",
            "JoannaClock",
            "123456"
        )
        val id2 = base.addWithNewId(user2)

        Assertions.assertEquals(id1, base.getByEmail("john@smith.com"))
        Assertions.assertEquals(id2, base.getByEmail("jonanna@clock.org"))
        Assertions.assertEquals(null, base.getByEmail("peitbe@rast.uk"))
        Assertions.assertEquals(null, base.getByEmail("+3(938)781-23-57"))
    }

    @Test
    fun getByPhoneNumberTest() {
        val base: UserDao by inject()

        val user1 = User(
            123,
            "John",
            "john@smith.com",
            "+3(938)781-23-57",
            "JohnSmith",
            "qwerty"
        )
        val id1 = base.addWithNewId(user1)

        val user2 = User(
            124,
            "Joanna",
            "jonanna@clock.org",
            "+9(042)222-33-44",
            "JoannaClock",
            "123456"
        )
        val id2 = base.addWithNewId(user2)

        Assertions.assertEquals(id1, base.getByPhoneNumber("+3(938)781-23-57"))
        Assertions.assertEquals(id2, base.getByPhoneNumber("+9(042)222-33-44"))
        Assertions.assertEquals(null, base.getByPhoneNumber("+8(142)284-61-36"))
        Assertions.assertEquals(null, base.getByPhoneNumber("john@smith.com"))
    }
}
