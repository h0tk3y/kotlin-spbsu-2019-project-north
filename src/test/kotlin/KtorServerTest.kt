import io.ktor.application.Application
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import org.koin.test.inject
import kotlin.test.assertEquals

class KtorServerTest : DBTest() {
    fun withAppAndServer(test: TestApplicationEngine.(Server) -> Unit) {
        withTestApplication(Application::main) {
            val server: Server by inject()
            test(server)
        }
    }

    fun TestApplicationEngine.withLoginRequest(
        username: String,
        password: String,
        code: TestApplicationCall.() -> Unit
    ) {
        with(handleRequest(HttpMethod.Post, "/login") {
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody("""{"username": "$username", "password": "$password"}""")
        }, code)
    }

    @Test
    fun testLoginOK() {
        withAppAndServer { server ->
            server.register("Antoha", "shananton@kek.lol", "+7654382145", "shananton", "qwerty123")
            withLoginRequest("shananton", "qwerty123") {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }


    @Test
    fun testLoginFail() {
        withAppAndServer { server ->
            server.register("Antoha", "shananton@kek.lol", "+7654382145", "shananton", "qwerty123")
            withLoginRequest("shananton", "eye4goat") {
                assertEquals(HttpStatusCode.Forbidden, response.status())
            }
        }
    }

    @Test
    fun testRegister() {
    }

    @Test
    fun testLogOut() {

    }
}