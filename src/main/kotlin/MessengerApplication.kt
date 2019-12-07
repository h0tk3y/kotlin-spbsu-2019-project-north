import dao.UserId
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import model.User
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

data class LoginRequest(val username: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val phoneNumber: String, val username: String, val password: String)

fun main() {
//    val server =
}

fun Application.main() {
    MessengerApplication().apply { main() }
}

class MessengerApplication {
    fun Application.main() {
        install(Koin) {
            modules(listOf(daoModule, apiModule))
        }

        install(ContentNegotiation) {
            jackson {}
        }

        install(StatusPages) {
            exception<Throwable> {
                call.respond(HttpStatusCode.InternalServerError, "Internal server error")
            }
        }

        val server: Server by inject()
        install(Authentication) {
            jwt {
                realm = JwtConfig.realm
                verifier(JwtConfig.verifier)
                validate { it.payload.getClaim("id").asLong()?.let(server::getUserById) }
            }
        }

        routing {
            post("/login") {
                val request = call.receive<LoginRequest>()
                val credentials = UserPasswordCredential(request.username, request.password)
                when (val user = server.getUserByCredentials(credentials)) {
                    null -> call.respond(HttpStatusCode.Forbidden, "Invalid login/password pair")
                    else -> call.respond(HttpStatusCode.OK, JwtConfig.makeToken(user))
                }
            }
            post("/register") {
                call.respond(
                    HttpStatusCode.OK,
                    with (call.receive<RegisterRequest>()) {
                        JwtConfig.makeToken(server.register(name, email, phoneNumber, username, password))
                    }
                )
            }
            authenticate {
                val getByIdRequestsMap: Map<String, (Server, UserId) -> List<Any>> = mapOf(
                    "/getChats" to Server::getChats,
                    "/getPersonalChats" to Server::getPersonalChats,
                    "/getGroupChats" to Server::getGroupChats,
                    "/getContacts" to Server::getContacts
                )
                for ((path, function) in getByIdRequestsMap) {
                    get(path) {
                        call.respond(HttpStatusCode.OK, function(server, call.principal<User>()!!.id.value))
                    }
                }
//                get("/getChatMessages") {
//                    when (val id = call.parameters["id"]?.toLong()) {
//                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid id")
//                        else -> call.respond(HttpStatusCode.OK, server.getChatMessages(id))
//                    }
//                }
//                post("/sendMessage") {
//                    val chatId = call.parameters["chatId"]?.toLong()
//                    val userId = call.parameters["userId"]?.toLong()
//                    val text = call.parameters["text"]
//                    when (chatId) {
//                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid chatId")
//                        else -> when (userId) {
//                            null -> call.respond(HttpStatusCode.Forbidden, "Invalid userId")
//                            else -> when (text) {
//                                null -> call.respond(HttpStatusCode.Forbidden, "Invalid text")
//                                else -> call.respond(HttpStatusCode.OK, server.sendMessage(chatId, userId, text))
//                            }
//                        }
//                    }
//                }
//                post("/createGroupChat") {
//                    val userId = call.parameters["userId"]?.toLong()
//                    val name = call.parameters["name"]
//                    when (userId) {
//                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid userId")
//                        else -> when (name) {
//                            null -> call.respond(HttpStatusCode.Forbidden, "Invalid name")
//                            else -> call.respond(HttpStatusCode.OK, server.createGroupChat(userId, name))
//                        }
//                    }
//                }
//                post("/createPersonalChat") {
//                    val user1 = call.parameters["user1"]?.toLong()
//                    val user2 = call.parameters["user2"]?.toLong()
//                    if (user1 == null || user2 == null)
//                        call.respond(HttpStatusCode.Forbidden, "Invalid userId")
//                    else
//                        call.respond(HttpStatusCode.OK, server.createPersonalChat(user1, user2))
//                }
            }
        }
    }
}