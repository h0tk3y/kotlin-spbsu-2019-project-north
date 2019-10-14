
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import io.ktor.util.toMap
import org.json.simple.JSONObject

val DB = mutableMapOf<String, String>("log" to "pass")
//http://127.0.0.1:8080/login?login=log&password=pass
//http://127.0.0.1:8080/session
fun main(args: Array<String>) {
    data class IndexData(val value: List<Int>)
    data class MySession(val name: String?, val value: Int)

    val server = embeddedServer(Netty, port = 8080) {
        routing {
            install(Sessions) { cookie<MySession>("COOKIE_NAME")
            }
            install(ContentNegotiation) {
                jackson {}
            }
            get("/login") {
                val login = call.request.queryParameters["login"]
                val password = call.request.queryParameters["password"]
                when {
                    login == null || password == null -> call.respondText("Логин или пароль null")
                    login in DB.keys && DB[login] == password -> {
                        call.sessions.set(MySession(login, 239))
                        call.respondText("OK")
                    }
                    else -> call.respondText("Неправильный логин или пароль")
                }
            }
            get("/register") {
                val login = call.request.queryParameters["login"]
                val password = call.request.queryParameters["password"]
                when {
                    login == null || password == null -> call.respondText("Логин или пароль null")
                    login in DB.keys -> call.respondText("Пользователь с таким именем уже существует")
                    password.length < 6 -> call.respondText("Пароль слишком короткий")
                    else -> {
                        DB[login] = password
                        call.respondText("OK")
                    }
                }
            }
            get("/session") {
                val session = call.sessions.get<MySession>() ?: MySession(name = "Empty", value = 0)
                call.sessions.set(session.copy(value = session.value + 1))
                call.respondText("Current session : ${session.name}")
            }
            get("/logout") {
                val session = call.sessions.get<MySession>() ?: MySession(name = "Empty", value = 0)
                call.sessions.clear<MySession>()
                call.respondText("Logged out : ${session.name}")
            }
        }
    }
    server.start(wait = true)
}