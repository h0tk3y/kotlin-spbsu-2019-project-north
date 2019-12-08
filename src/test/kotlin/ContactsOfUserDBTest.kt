//import dao.ContactsOfUserDao
//import model.Contact
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.koin.test.inject
//
//class ContactsOfUserDBTest : DBTest {
//    @Test
//    fun addAndContainsTest() {
//        val base: ContactsOfUserDao by inject()
//        val alya = Contact(1, "Alya")
//        val antoha = Contact(2, "Antoha")
//
//        base.add(1, antoha)
//        base.add(2, alya)
//
//        Assertions.assertTrue(base.contains(1, antoha))
//        Assertions.assertTrue(base.contains(2, alya))
//        Assertions.assertFalse(base.contains(1, alya))
//    }
//
//    @Test
//    fun removeTest() {
//        val base: ContactsOfUserDao by inject()
//        val alya = Contact(1, "Alya")
//        val antoha = Contact(2, "Antoha")
//
//        base.add(1, antoha)
//        base.add(2, alya)
//
//        base.remove(1, antoha)
//
//        Assertions.assertFalse(base.contains(1, antoha))
//        Assertions.assertTrue(base.contains(2, alya))
//    }
//
//    @Test
//    fun selectTest() {
//        val base: ContactsOfUserDao by inject()
//        val alya = Contact(1, "Alya")
//        val antoha = Contact(2, "Antoha")
//
//        base.add(1, antoha)
//        base.add(2, alya)
//        base.add(1, alya)
//
//        Assertions.assertTrue(base.select(1).size == 2)
//        Assertions.assertTrue(base.select(2).size == 1)
//    }
//
//    @Test
//    fun changeNameTest() {
//        val base: ContactsOfUserDao by inject()
//        val alya = Contact(1, "Alya")
//        val antoha = Contact(2, "Antoha")
//
//        base.add(1, antoha)
//        base.add(2, alya)
//
//        base.changeName(1, 2, "Kartoha")
//
//        Assertions.assertTrue(base.select(1)[0].name == "Kartoha")
//        Assertions.assertTrue(base.select(2)[0].name == "Alya")
//    }
//}