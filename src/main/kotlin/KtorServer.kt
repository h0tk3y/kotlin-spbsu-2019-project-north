import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import org.json.simple.JSONObject
import java.security.MessageDigest
import java.time.Instant
import java.time.format.DateTimeFormatter
import org.koin.ktor.ext.Koin
import org.koin.Logger.slf4jLogger

private val DB = mutableMapOf<String, Int>("log" to "pass".hashCode())

data class Credentials(val login: String?, val passHash: Int?)

private fun hashString(input: String): String {
    val HEX_CHARS = "0123456789ABCDEF"
    val bytes = MessageDigest
        .getInstance("SHA-256")
        .digest(input.toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(HEX_CHARS[i shr 4 and 0x0f])
        result.append(HEX_CHARS[i and 0x0f])
    }

    return result.toString()
}

fun main(args: Array<String>) {
    data class MySession(val name: String?, val time: String)

    val server = embeddedServer(Netty, port = 8080) {
        routing {
            install(Sessions) {
                cookie<MySession>("COOKIE_NAME")
            }
            install(ContentNegotiation) {
                jackson {}
            }
            install(Koin) {
                slf4jLogger()
                modules(daoModule)
            }
            post("/login") {
                val creds = call.receive<Credentials>()
                val login = creds.login
                val passHash = creds.passHash
                when {
                    login == null || passHash == null ->
                        call.respond("status" to "Login or password is missing")
                    //call.respond(mapOf("status" to "Login or password is missing"), ContentType.Application.Json)
                    login in DB.keys && DB[login] == passHash -> {
                        val time = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                        val token = hashString(creds.toString() + time)
                        call.sessions.set(MySession(login, time))
                        call.respond(mapOf("status" to "OK")) // TODO: CONTENT TYPE
                    }
                    else -> call.respond(mapOf("status" to "Неправильный логин или пароль"))
                }
            }
            post("/register") {
                val login = call.request.queryParameters["login"]
                val password = call.request.queryParameters["password"]
                when {
                    login == null || password == null -> call.respondText("Логин или пароль null")
                    login in DB.keys -> call.respondText("Пользователь с таким именем уже существует")
                    password.length < 6 -> call.respondText("Пароль слишком короткий")
                    else -> {
                        DB[login] = password.hashCode()
                        call.respondText("OK")
                    }
                }
            }
            get("/logout") {
                val session = call.sessions.get<MySession>() ?: MySession(name = "None", time = "")
                call.sessions.clear<MySession>()
                call.respondText("Logged out : ${session.name}")
            }
        }
    }
    server.start(wait = true)
}