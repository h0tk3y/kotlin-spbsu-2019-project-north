import com.fasterxml.jackson.databind.exc.MismatchedInputException
import dao.Id
import dao.UserId
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondOutputStream
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import model.User
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

data class LoginRequest(val username: String, val password: String)
data class RegisterRequest(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val username: String,
    val password: String
)

data class GetChatMessagesRequest(
    val chatId: Id,
    val isPersonal: Boolean,
    val block: Int,
    val last: Int?
)

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
            exception<InvalidRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.reason)
            }
            exception<MismatchedInputException> {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            }
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
                    null -> {
                        call.response.header(HttpHeaders.WWWAuthenticate, "Bearer realm=${JwtConfig.realm}")
                        call.respond(HttpStatusCode.Unauthorized, "Invalid username/password pair")
                    }
                    else -> call.respond(HttpStatusCode.OK, JwtConfig.makeToken(user))
                }
            }
            post("/register") {
                call.respond(
                    HttpStatusCode.OK,
                    with(call.receive<RegisterRequest>()) {
                        JwtConfig.makeToken(server.register(name, email, phoneNumber, username, password))
                    }
                )
            }
            authenticate {
                //TODO(Test those 4 methods:)
                val getByIdRequestsMap: Map<String, (Server, UserId) -> List<Any>> = mapOf(
                    "/getChats" to Server::getChats,
                    "/getPersonalChats" to Server::getPersonalChats,
                    "/getGroupChats" to Server::getGroupChats,
                    "/getContacts" to Server::getContacts
                )
                for ((path, function) in getByIdRequestsMap) {
                    post(path) {
                        call.respond(HttpStatusCode.OK, function(server, call.principal<User>()!!.id.value))
                    }
                }
                get("/getChatMessages") {
                    call.respond(HttpStatusCode.OK,
                        with(call.receive<GetChatMessagesRequest>()) {
                            //TODO(Check is the user belongs to the chat!!!)
                            server.getChatMessages(chatId, isPersonal, block, last)
                        }
                    )
                }
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