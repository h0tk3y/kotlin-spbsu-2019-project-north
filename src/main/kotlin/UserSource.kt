import io.ktor.auth.*

interface UserSource {

    fun findUserById(id: Long): User

    fun findUserByCredentials(credential: UserPasswordCredential): User

}