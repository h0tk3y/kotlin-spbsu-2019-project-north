import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

open class DBTestWithKoin : DBTest() {
    @BeforeEach
    fun startKoinInstance() {
        startKoin { modules(listOf(daoModule, apiModule)) }
    }

    @AfterEach
    fun stopKoinInstance() {
        stopKoin()
    }
}
