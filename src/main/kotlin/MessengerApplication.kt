import com.fasterxml.jackson.databind.exc.MismatchedInputException
import dao.Id
import dao.UserId
import io.ktor.application.Application
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
import io.ktor.routing.post
import io.ktor.routing.routing
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
    val personal: Boolean
)

data class GetChatMessagesResponse(
    val messages: List<Message>
)

data class SendMessageRequest(
    val chatId: Id,
    val isPersonal: Boolean,
    val text: String
)

data class UserIdPrincipal(val id: UserId) : Principal

data class PermissionDeniedException(val reason: String) : Exception()

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
            exception<PermissionDeniedException>() { cause ->
                call.respond(HttpStatusCode.Forbidden, cause.reason)
            }
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
                validate { UserIdPrincipal(it.payload.getClaim("id").asLong()!!) }
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
                        call.respond(HttpStatusCode.OK, function(server, call.principal<UserIdPrincipal>()!!.id))
                    }
                }
                post("/getChatMessages") {
                    call.respond(HttpStatusCode.OK,
                        with(call.receive<GetChatMessagesRequest>()) {
                            when (chatId) {
                                in server.getChats(call.principal<UserIdPrincipal>()!!.id) ->
                                    GetChatMessagesResponse(server.getChatMessages(chatId, personal))
                                else ->
                                    throw PermissionDeniedException("You are not a member of this chat")
                            }
                        }
                    )
                }
                post("/sendMessage") {
                    call.respond(HttpStatusCode.OK,
                        with(call.receive<SendMessageRequest>()) {
                            val userId = call.principal<UserIdPrincipal>()!!.id
                            when (chatId) {
                                in server.getChats(call.principal<UserIdPrincipal>()!!.id) ->
                                    server.sendMessage(userId, isPersonal, chatId, text)!!
                                else ->
                                    throw PermissionDeniedException("You are not a member of this chat")
                            }
                        }
                    )
                }
//                post("/createGroupChat") {
//
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