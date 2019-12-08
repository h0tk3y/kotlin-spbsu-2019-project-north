import io.ktor.application.Application
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.HttpStatusCodeContent
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import org.koin.test.inject
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class KtorServerTest : DBTest() {
    private fun withAppAndServer(test: TestApplicationEngine.(Server) -> Unit) {
        withTestApplication(Application::main) {
            val server: Server by inject()
            test(server)
        }
    }

    private fun TestApplicationEngine.withJsonRequest(uri: String, json: String, code: TestApplicationCall.() -> Unit) {
        with(handleRequest(HttpMethod.Post, uri) {
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(json)
        }, code)
    }

    private fun TestApplicationEngine.withLoginRequest(
        username: String,
        password: String,
        code: TestApplicationCall.() -> Unit
    ) {
        withJsonRequest("/login", """{"username": "$username", "password": "$password"}""", code)
    }

    private fun TestApplicationEngine.withRegisterRequest(
        name: String,
        email: String,
        phoneNumber: String,
        username: String,
        password: String,
        code: TestApplicationCall.() -> Unit
    ) {
        withJsonRequest(
            "/register",
            """{"name": "$name", "email": "$email", "phoneNumber": "$phoneNumber", "username": "$username", "password": "$password"}""",
            code
        )
    }

    private fun TestApplicationEngine.withJsonJwtRequest(uri: String, json: String, jwt: String, code: TestApplicationCall.() -> Unit) {
        with(handleRequest(HttpMethod.Post, uri) {
            addHeader(HttpHeaders.Authorization, "Bearer $jwt")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(json)
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
                assertEquals(HttpStatusCode.Unauthorized, response.status())
                assertEquals("Invalid username/password pair", response.content)
            }
        }
    }

    @Test
    fun testRegisterOK() {
        withAppAndServer {
            withRegisterRequest("Antoha", "shananton@kek.lol", "+7938529835", "shananton", "qwerty123") {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }


    @Test
    fun testRegisterBadEmail() {
        withAppAndServer {
            withRegisterRequest("Antoha", "shananton@@kek.lol", "+7938529835", "shananton", "qwerty123") {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals("Email must contain exactly one '@'", response.content)
            }
        }
    }

    @Test
    fun testUseIssuedJwtTokenToAuthorizeRequestOK() {
        withAppAndServer { server ->
            server.register("Antoha", "shananton@kek.lol", "+7654382145", "shananton", "qwerty123")
            var token: String = ""
            withLoginRequest("shananton", "qwerty123") {
                assertEquals(HttpStatusCode.OK, response.status())
                assertFalse(response.content.isNullOrEmpty())
                token = response.content!!
            }
            withJsonJwtRequest("/getChats", "", token) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testUseInvalidJwtTokenToAuthorizeFail() {
        withAppAndServer { server ->
            server.register("Antoha", "shananton@kek.lol", "+7654382145", "shananton", "qwerty123")
            var token: String = ""
            withLoginRequest("shananton", "qwerty123") {
                assertEquals(HttpStatusCode.OK, response.status())
                assertFalse(response.content.isNullOrEmpty())
                token = response.content!!
            }
            withJsonJwtRequest("/getChats", "", token + "1") {
                assertEquals(HttpStatusCode.Unauthorized, response.status())
            }
        }
    }

    @Test
    fun testUseSelfIssuedJwtTokenOK() {
        withAppAndServer {server ->
            val user = server.register("Antoha", "shananton@kek.lol", "+7654382145", "shananton", "qwerty123")
            withJsonJwtRequest("/getChats", "", JwtConfig.makeToken(user)) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testUseExpiredJwtTokenFail() {
        withAppAndServer {server ->
            val user = server.register("Antoha", "shananton@kek.lol", "+7654382145", "shananton", "qwerty123")
            withJsonJwtRequest("/getChats", "", JwtConfig.makeToken(user, Date(System.currentTimeMillis() - 1000))) {
                assertEquals(HttpStatusCode.Unauthorized, response.status())
            }
        }
    }


//    @Test
//    fun testLogOut() {
//
//    }
}