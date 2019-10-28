import io.ktor.application.ApplicationCall
import io.ktor.auth.*

val ApplicationCall.user get() = authentication.principal<User>()
val testUser = User(
    1,
    "Anton",
    "shananton2000@gmail.com",
    "89834429756",
    "shananton",
    "kek12345"
)

class UserSourceImpl : UserSource {

    override fun findUserById(id: Long): User = users.getValue(id)

    override fun findUserByCredentials(credential: UserPasswordCredential): User = testUser

    private val users = listOf(testUser).associateBy(User::id)

}