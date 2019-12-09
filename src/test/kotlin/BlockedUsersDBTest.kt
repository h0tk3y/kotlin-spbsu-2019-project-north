import dao.BlockedUsersDao
import dao.UserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.inject

class BlockedUsersDBTest : DBTestWithKoin() {
    @Test
    fun allTest() {
        val base: BlockedUsersDao by inject()
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

        base.block(alya, nikita)
        base.block(nikita, alya)
        base.block(alya, antoha)
        base.block(antoha, alya)

        Assertions.assertTrue(base.isBlocked(alya, antoha))
        Assertions.assertTrue(base.isBlocked(antoha, alya))
        Assertions.assertTrue(base.isBlocked(alya, nikita))
        Assertions.assertTrue(base.isBlocked(nikita, alya))

        base.unblock(alya, antoha)
        Assertions.assertFalse(base.isBlocked(alya, antoha))
        Assertions.assertTrue(base.isBlocked(antoha, alya))

        base.unblock(alya, nikita)
        base.unblock(alya, nikita)
        base.unblock(antoha, nikita)

        base.block(nikita, antoha)

        Assertions.assertEquals(0, base.select(alya).size)
        Assertions.assertEquals(1, base.select(antoha).size)
        Assertions.assertEquals(2, base.select(nikita).size)
    }
}