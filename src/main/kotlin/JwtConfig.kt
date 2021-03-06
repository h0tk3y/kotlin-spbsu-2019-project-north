import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {
    private const val issuer = "snailmail.north"
    const val realm = "snailmail.north.app"

    private const val secret = "keklol" //TODO(nani?)
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun makeToken(user: User, expirationDate: Date = getExpirationDate()): String = JWT.create()
        .withSubject("authentication")
        .withIssuer(issuer)
        .withClaim("id", user.id)
        .withExpiresAt(expirationDate)
        .sign(algorithm)

    val validityInMs = 1000 * 60 * 60 * 5 //5 hours
    private fun getExpirationDate() = Date(System.currentTimeMillis() + validityInMs)
}