import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import io.ktor.util.Hash
import io.ktor.util.toMap
import org.json.simple.JSONObject
import java.security.MessageDigest
import java.util.*

//private fun hashString(input: String): String {
//    val HEX_CHARS = "0123456789ABCDEF"
//    val bytes = MessageDigest
//        .getInstance("SHA-256")
//        .digest(input.toByteArray())
//    val result = StringBuilder(bytes.size * 2)
//
//    bytes.forEach {
//        val i = it.toInt()
//        result.append(HEX_CHARS[i shr 4 and 0x0f])
//        result.append(HEX_CHARS[i and 0x0f])
//    }
//
//    return result.toString()
//}

//private var maxId = 0
//private var curSessionNumber = 1
//private val DB = mutableMapOf<String, String>("log" to hashString("pass"))
//private val idByLogin = mutableMapOf<String, Int>("log" to 0)

//data class Credentials(val login: String?, val passHash: String?)

fun main(args: Array<String>) {
//    data class MySession(val id: Int?, val number: Int?, val token: String)

    val server = embeddedServer(Netty, port = 8080) {
        routing {
            val userSource: UserSource = UserSourceImpl()
            install(Authentication) {
                jwt {
                    verifier(JwtConfig.verifier)
                    realm = "ktor.io"
                    validate {
                        it.payload.getClaim("id").asLong()?.let(userSource::findUserById)
                    }
                }
            }
//            install(Sessions) {
//                cookie<MySession>("COOKIE_NAME")
//            }
            install(ContentNegotiation) {
                jackson {}
            }
//            post("/login") {
//                val creds = call.receive<Credentials>()
//                val login = creds.login
//                val passHash = creds.passHash
//                when {
//                    login == null || passHash == null ->
//                        call.respond(JSONObject(mutableMapOf(Pair("status", "Login or password is missing"))))
//                        //call.respond(mapOf("status" to "Login or password is missing"), ContentType.Application.Json)
//                    login in DB.keys && DB[login] == passHash -> {
//                        val token = hashString(creds.toString() + curSessionNumber)
//                        call.sessions.set(MySession(idByLogin[login], curSessionNumber++, token))
//                        call.respond(mapOf("status" to "OK")) //TODO CONTENT TYPE
//                    }
//                    else -> call.respond(mapOf("status" to "Неправильный логин или пароль"))
//                }
//            }
//            post("/register") {
//                val login = call.request.queryParameters["login"]
//                val password = call.request.queryParameters["password"]
//                when {
//                    login == null || password == null -> call.respondText("Логин или пароль null")
//                    login in DB.keys -> call.respondText("Пользователь с таким именем уже существует")
//                    password.length < 6 -> call.respondText("Пароль слишком короткий")
//                    else -> {
//                        DB[login] = hashString(password)
//                        idByLogin[login] = ++maxId
//                        call.sessions.set(MySession(maxId, curSessionNumber++, token))
//                        call.respondText("OK")
//                    }
//                }
//            }
//            get("/logout") {
//                val session = call.sessions.get<MySession>()
//                call.sessions.clear<MySession>()
//                if (session != null)
//                    call.respondText("Logged out : user #${session.id}")
//            }
            post("login") {
                val credentials = call.receive<UserPasswordCredential>()
                val user = userSource.findUserByCredentials(credentials)
                val token = JwtConfig.makeToken(user)
                call.respondText(token)
            }
            authenticate {
                route("secret") {

                    get {
                        val user = call.user!!
                        call.respond(user.name)
                    }

                    put {
                        TODO("All your secret routes can follow here")
                    }

                }
            }
            authenticate(optional = true) {
                get("optional") {
                    val user = call.user
                    val response = if (user != null) "authenticated!" else "optional"
                    call.respond(response)
                }
            }
        }
    }
    server.start(wait = true)
}