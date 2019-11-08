import dao.BlockedUsersDao
import dao.ChatsOfUserDao
import dao.MessageDao
import dao.RelationsDao
import model.GroupChat
import model.PersonalChat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class ChatsOfUserDBTest : DBTest {
    @Test
    fun addAndContainsTest() {
        val base: ChatsOfUserDao by inject()

        base.add(1, 1)
        base.add(1, 2)
        base.add(2, 1)
        Assertions.assertTrue(base.contains(1, 1))
        Assertions.assertTrue(base.contains(1, 2))
        Assertions.assertTrue(base.contains(2, 1))
    }

    @Test
    fun removeTest() {
        val base: ChatsOfUserDao by inject()

        base.add(1, 1)
        base.add(1, 2)
        base.add(2, 1)
        base.remove(2, 1)
        Assertions.assertTrue(base.contains(1, 1))
        Assertions.assertTrue(base.contains(1, 2))
        Assertions.assertFalse(base.contains(2, 1))
    }

    @Test
    fun selectTest() {
        val base: ChatsOfUserDao by inject()

        base.add(1, 1)
        base.add(1, 2)
        base.add(2, 1)
        Assertions.assertTrue(base.select(1).size == 2)
        Assertions.assertTrue(base.select(2).size == 1)
    }
}