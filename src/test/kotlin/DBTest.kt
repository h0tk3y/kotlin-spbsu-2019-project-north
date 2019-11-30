import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import tables.*

var isConnected: Boolean = false

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
interface DBTest : KoinTest {

    @BeforeEach
    fun start() {
        startKoin { modules(daoModule) }
        if (!isConnected) {
            Database.connect(
                "jdbc:postgresql://localhost:5432/testdb", driver = "org.postgresql.Driver",
                user = "testuser", password = "123456"
            )
            isConnected = true
        }
        transaction {
            SchemaUtils.drop(Contacts)
            SchemaUtils.drop(GroupChatsToUsers)
            SchemaUtils.drop(GroupChats)
            SchemaUtils.drop(Messages)
            SchemaUtils.drop(PersonalChats)
            SchemaUtils.drop(Users)
        }
        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.create(GroupChats)
            SchemaUtils.create(PersonalChats)
            SchemaUtils.create(Messages)
            SchemaUtils.create(GroupChatsToUsers)
            SchemaUtils.create(Contacts)
        }
    }


    @AfterEach
    fun stop() {
        stopKoin()
    }

}

//object ConnectDB {
//    init {
////          Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")
////        Database.connect("jdbc:h2:mem:regular", "org.h2.Driver")
////        Database.connect("jdbc:sqlite:file:test?mode=memory&cache=shared", "org.sqlite.JDBC")
//    }
//}