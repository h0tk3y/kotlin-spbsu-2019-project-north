import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.koin.test.KoinTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import tables.*

internal class SpecifiedPostgreSQLContainer(val image: String) :
    PostgreSQLContainer<SpecifiedPostgreSQLContainer>(image)

@Testcontainers
open class DBTest : KoinTest {

    companion object {
        @Container
        @JvmField
        internal val postgres = PostgreSQLContainer<SpecifiedPostgreSQLContainer>()

        @BeforeAll
        @JvmStatic
        internal fun initContainer() {
            postgres.start()
            Database.connect(
                postgres.jdbcUrl, driver = "org.postgresql.Driver",
                user = postgres.username, password = postgres.password
            )
        }

        @AfterAll
        @JvmStatic
        internal fun closePostgres() = postgres.close()
    }


    @BeforeEach
    fun start() {
        transaction {
            SchemaUtils.drop(BlockedUsers)
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
            SchemaUtils.create(BlockedUsers)
        }
    }
}