import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.url
import org.json.simple.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

//suspend fun testLogin() {
//    val client = HttpClient()
//    val DB = mutableMapOf<String, Int>("log" to "pass".hashCode())
//    val request = client.post<JSONObject> {
//        url("http://127.0.0.1:8080/login")
//        body = Credentials("log", "pass".hashCode())
//    }
//    print(request.toJSONString())
//}

class KtorServerTest {
    @Test
    fun testRegister() {
        assert(true)
    }

    @Test
    fun testLogOut() {
        assert(true)
    }
}

suspend fun main() {
    //testLogin()
    println("§")
}