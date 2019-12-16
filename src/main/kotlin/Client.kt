import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.JSONWrappedObject
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dao.GroupChatId
import dao.Id
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.*
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.utils.EmptyContent
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.parseResponse
import io.ktor.http.content.TextContent
import io.ktor.http.contentType

class Client() {
    private var token: String? = null
    private val serverHost = "127.0.0.1"
    private val serverPort = 8080
    private val httpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
    }

    suspend fun greet() = "Welcome to SnailMail" //postRequest("/").readText()
    private suspend fun postRequest(path: String, body: Any = EmptyContent): HttpResponse =
        httpClient.post {
            token?.let { header("Authorization", "Bearer $it") }
            url {
                host = serverHost
                port = serverPort
                encodedPath = path
            }
            this.body = TextContent(
                ObjectMapper().writeValueAsString(body),
                ContentType.Application.Json
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

    suspend fun sendMessage(messageRequest: SendMessageRequest): String? =
        try {
            val response = postRequest("/sendMessage", messageRequest)
            if (response.status == HttpStatusCode.Unauthorized)
                "Please log in"
            else
                null
        } catch (e: PermissionDeniedException) {
            e.reason
        }

    suspend fun getChats(isPersonal: Boolean?): List<GroupChatId>? {
        val urlString = when (isPersonal) {
            true -> "/getPersonalChats"
            false -> "/getGroupChats"
            null -> "/getChats"
        }
        val response = postRequest(urlString)
        return when (response.status) {
            HttpStatusCode.OK -> response.receive<List<GroupChatId>>()
            else -> null
        }
    }
    //fun createPublicChat(name: String) {}
    //fun createPrivateChat(name: String) {}
    //fun createPersonalChat(user2: User) {}

    //fun inviteToChat(user2: User) {}
}