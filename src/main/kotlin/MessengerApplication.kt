import dao.UserDao
import dao.UserId
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.Credential
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import kotlin.reflect.KCallable
import kotlin.reflect.KFunction

fun main() {
//    val server =
}

fun Application.main() {
    MessengerApplication().apply { main() }
}

class MessengerApplication {
    fun Application.main() {
        install(Koin) {
            modules(daoModule)
        }

        install(ContentNegotiation) {
            jackson {}
        }

        val server = Server()
        install(Authentication) {
            jwt {
                realm = JwtConfig.realm
                verifier(JwtConfig.verifier)
                validate { it.payload.getClaim("id").asLong()?.let(server.userBase::getById) }
            }
        }

        routing {
            post("/login") {
                val credentials = call.receive<UserPasswordCredential>()
                when(val user = server.getUserByCredentials(credentials)) {
                    null -> call.respond(HttpStatusCode.Forbidden, "Invalid login/password pair")
                    else -> call.respond(HttpStatusCode.OK, JwtConfig.makeToken(user))
                }
            }
            post("/register") {
                val name = call.parameters["name"]
                val email = call.parameters["email"]
                val phoneNumber = call.parameters["phoneNumber"]
                val login = call.parameters["login"]
                val password = call.parameters["password"]
                when (name) {
                    null -> call.respond(HttpStatusCode.Forbidden, "Invalid name")
                    else -> when {
                        email == null -> call.respond(HttpStatusCode.Forbidden, "Invalid email")
                        !email.contains('@') -> call.respond(HttpStatusCode.Forbidden, "Incorrect email")
                        else -> when {
                            phoneNumber == null -> call.respond(HttpStatusCode.Forbidden, "Invalid email")
                            phoneNumber.chars().anyMatch(Character::isLetter) ->
                                call.respond(HttpStatusCode.Forbidden, "Incorrect phone number")
                            else -> when {
                                login == null -> call.respond(HttpStatusCode.Forbidden, "Invalid login")
                                //TODO check if user with such login already exists
                                else -> when {
                                    password == null ->
                                        call.respond(HttpStatusCode.Forbidden, "Invalid password")
                                    password.length < 6 ->
                                        call.respond(HttpStatusCode.Forbidden, "Password is too short")
                                    else -> call.respond(HttpStatusCode.OK,
                                        {
                                            val user = server.register(name, email, phoneNumber, login, password)
                                            JwtConfig.makeToken(user)
                                        })
                                }
                            }
                        }
                    }
                }
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
                        when (val id = call.parameters["userId"]?.toLong()) {
                            null -> call.respond(HttpStatusCode.Forbidden, "Invalid id")
                            else -> call.respond(HttpStatusCode.OK, function(server, id))
                        }
                    }
                }
                get("/getChatMessages") {
                    when (val id = call.parameters["id"]?.toLong()) {
                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid id")
                        else -> call.respond(HttpStatusCode.OK, server.getChatMessages(id))
                    }
                }
                post("/sendMessage") {
                    val chatId = call.parameters["chatId"]?.toLong()
                    val userId = call.parameters["userId"]?.toLong()
                    val text = call.parameters["text"]
                    when (chatId) {
                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid chatId")
                        else -> when (userId) {
                            null -> call.respond(HttpStatusCode.Forbidden, "Invalid userId")
                            else -> when (text) {
                                null -> call.respond(HttpStatusCode.Forbidden, "Invalid text")
                                else -> call.respond(HttpStatusCode.OK, server.sendMessage(chatId, userId, text))
                            }
                        }
                    }
                }
                post("/createGroupChat") {
                    val userId = call.parameters["userId"]?.toLong()
                    val name = call.parameters["name"]
                    when (userId) {
                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid userId")
                        else -> when (name) {
                            null -> call.respond(HttpStatusCode.Forbidden, "Invalid name")
                            else -> call.respond(HttpStatusCode.OK, server.createGroupChat(userId, name))
                        }
                    }
                }
                post("/createPersonalChat") {
                    val user1 = call.parameters["user1"]?.toLong()
                    val user2 = call.parameters["user2"]?.toLong()
                    if (user1 == null || user2 == null)
                        call.respond(HttpStatusCode.Forbidden, "Invalid userId")
                    else
                        call.respond(HttpStatusCode.OK, server.createPersonalChat(user1, user2))
                }
            }
        }
    }
}