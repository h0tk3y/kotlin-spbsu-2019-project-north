import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import tables.*

interface DBTest : KoinTest {
    @BeforeEach
    fun start() {
        startKoin { modules(daoModule) }
        transaction {
            SchemaUtils.create(Contacts)
            SchemaUtils.create(GroupChats)
            SchemaUtils.create(GroupChatsToUsers)
            SchemaUtils.create(Messages)
            SchemaUtils.create(PersonalChats)
            SchemaUtils.create(Users)
        }
    }


    @AfterEach
    fun stop() {
        stopKoin()
    }

}

object ConnectDB {
    init {
          Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")
//        Database.connect("jdbc:h2:mem:regular", "org.h2.Driver")
//        Database.connect("jdbc:sqlite:file:test?mode=memory&cache=shared", "org.sqlite.JDBC")
    }
}