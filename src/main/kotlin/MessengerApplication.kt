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
import io.ktor.routing.post
import io.ktor.routing.routing
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

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

        val userDao: UserDao by inject()
        install(Authentication) {
            jwt {
                realm = JwtConfig.realm
                verifier(JwtConfig.verifier)
                validate { it.payload.getClaim("id").asLong()?.let(userDao::getById) }
            }
        }

        routing {
            post("login") {
                val credentials = call.receive<UserPasswordCredential>()
                when(val user = userDao.getUserByCredentials(credentials)) {
                    null -> call.respond(HttpStatusCode.Forbidden, "Invalid login/password pair")
                    else -> call.respond(HttpStatusCode.OK, JwtConfig.makeToken(user))
                }
            }
            authenticate {
                // Sending messages etc.
            }
        }
    }
}