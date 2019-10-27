import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

interface DBTest : KoinTest {
    @BeforeEach
    fun start() {
        startKoin { modules(daoModule) }
    }

    @AfterEach
    fun stop() {
        stopKoin()
    }
}