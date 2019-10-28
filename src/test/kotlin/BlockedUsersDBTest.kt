import dao.BlockedUsersDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class BlockedUsersDBTest : DBTest {
    @Test
    fun isBlockedBlockAndUnblockTest() {
        val base: BlockedUsersDao by inject()
        Assertions.assertEquals(false, base.isBlocked(1, 2))

        base.block(1, 2)
        Assertions.assertEquals(true, base.isBlocked(1, 2))
        Assertions.assertEquals(false, base.isBlocked(1, 3))
        Assertions.assertEquals(false, base.isBlocked(2, 1))

        base.block(1, 2)
        Assertions.assertEquals(true, base.isBlocked(1, 2))

        base.unblock(1, 2)
        Assertions.assertEquals(false, base.isBlocked(1, 2))
    }
}