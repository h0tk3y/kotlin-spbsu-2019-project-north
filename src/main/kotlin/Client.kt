import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.client.engine.apache.Apache

class Client() {
    private val host = "localhost"
    private val port = 8080
    private val httpClient = HttpClient(Apache)

    private suspend fun postRequest(path: String, body: Any): HttpResponse =
        httpClient.post {
            token?.let { header("Bearer", it) }
            url {
                this.host = host
                this.port = port
                encodedPath = path
            }
            this.body = body
        }

    lateinit var user: User
    var token: String? = null

    suspend fun register(registerRequest: RegisterRequest) {
        val response = postRequest("/register", registerRequest)
        token = response.readText()
    }

    fun login(login: String, password: String) {}
    fun logout() {}

    //fun sendMessage(id: ChatId, text: String) {}

    fun createPublicChat(name: String) {}
    fun createPrivateChat(name: String) {}
    fun createPersonalChat(user2: User) {}

    fun inviteToChat(user2: User) {}
}