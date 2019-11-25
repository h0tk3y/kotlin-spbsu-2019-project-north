import dao.GroupChatsOfUserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class GroupChatOfUserDBTest : DBTest {
    @Test
    fun addAndContainsTest() {
        val base: GroupChatsOfUserDao by inject()

        base.add(1, 1)
        base.add(1, 2)
        base.add(2, 1)
        Assertions.assertTrue(base.contains(1, 1))
        Assertions.assertTrue(base.contains(1, 2))
        Assertions.assertTrue(base.contains(2, 1))
    }

    @Test
    fun removeTest() {
        val base: GroupChatsOfUserDao by inject()

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
        val base: GroupChatsOfUserDao by inject()

        base.add(1, 1)
        base.add(1, 2)
        base.add(2, 1)
        Assertions.assertTrue(base.select(1).size == 2)
        Assertions.assertTrue(base.select(2).size == 1)
    }
}