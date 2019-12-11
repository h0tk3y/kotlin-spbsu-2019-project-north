import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent

class Client() {
    private var token: String? = null
    private val host = "localhost"
    private val port = 8080
    private val httpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    private suspend fun postRequest(path: String, body: Any): HttpResponse =
        httpClient.post {
            token?.let { header("Bearer", it) }
            url {
                this.host = host
                this.port = port
                encodedPath = path
            }
            this.body = TextContent(
                jacksonObjectMapper().writeValueAsString(body),
                contentType = ContentType.Application.Json
            )
        }

    suspend fun register(registerRequest: RegisterRequest): String? =
        try {
            val response = postRequest("/register", registerRequest)
            token = response.readText()
            null
        } catch (e: InvalidRequestException) {
            e.reason
        }


    suspend fun login(loginRequest: LoginRequest): String? {
        val response = postRequest("/login", loginRequest)
        return when (response.status) {
            HttpStatusCode.Unauthorized -> response.readText()
            HttpStatusCode.OK -> {
                token = response.readText()
                null
            }
            else -> "Unknown server error"
        }
    }

    fun logout() {
        token = null
    }

    //fun sendMessage(id: ChatId, text: String) {}

    //fun createPublicChat(name: String) {}
    //fun createPrivateChat(name: String) {}
    //fun createPersonalChat(user2: User) {}

    //fun inviteToChat(user2: User) {}
}