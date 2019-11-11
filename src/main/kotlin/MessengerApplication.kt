import dao.UserDao
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
            post("login") {
                val credentials = call.receive<UserPasswordCredential>()
                when(val user = server.getUserByCredentials(credentials)) {
                    null -> call.respond(HttpStatusCode.Forbidden, "Invalid login/password pair")
                    else -> call.respond(HttpStatusCode.OK, JwtConfig.makeToken(user))
                }
            }
            authenticate {
                val getByIdRequestsList= mapOf(
                    "/getChats" to Server::getChats,
                    "/getPersonalChats" to Server::getPersonalChats,
                    "/getGroupChats" to Server::getGroupChats,
                    "/getContacts" to Server::getContacts,
                    "/getChatMessages" to Server::getChatMessages
                )
                get("/getChats") {
                    when (val id = call.parameters["id"]?.toLong()) {
                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid id")
                        else -> call.respond(HttpStatusCode.OK, server.getChats(id))
                    }
                }
                get("/getPersonalChats") {
                    when (val id = call.parameters["id"]?.toLong()) {
                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid id")
                        else -> call.respond(HttpStatusCode.OK, server.getPersonalChats(id))
                    }
                }
                get("/getGroupChats") {
                    when (val id = call.parameters["id"]?.toLong()) {
                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid id")
                        else -> call.respond(HttpStatusCode.OK, server.getGroupChats(id))
                    }
                }
                get("/getContacts") {
                    when (val id = call.parameters["id"]?.toLong()) {
                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid id")
                        else -> call.respond(HttpStatusCode.OK, server.getContacts(id))
                    }
                }
                get("/getChatMessages") {
                    when (val id = call.parameters["id"]?.toLong()) {
                        null -> call.respond(HttpStatusCode.Forbidden, "Invalid id")
                        else -> call.respond(HttpStatusCode.OK, server.getChatMessages(id))
                    }
                }
                // Sending messages etc.
            }
        }
    }
}