suspend fun main() {
    ConsoleClient().runClient()
}

class ConsoleClient() {
    private val client = Client()
    private var isRunning = true

    private fun waitForUser() = print("> ")
    private fun printHelp() {
        println(
            """
            Usage:
                register - Register a new user
                exit - Quit the application
        """.trimIndent()
        )
    }

    private fun readEntry(
        entryName: String,
        validate: (String) -> Boolean = { true },
        errorMsg: String = "Invalid $entryName"
    ): String {
        println("Enter $entryName:")
        waitForUser()
        val entry = readLine()?.trimIndent() ?: ""
        if (entry == "" || !validate(entry)) {
            println(errorMsg)
            return readEntry(entryName, validate, errorMsg)
        }
        return entry
    }

    private fun validateEmail(email: String) =
        Regex("[^@]+@[^\\.]+\\..+").matches(email)

    private fun validatePhoneNumber(phoneNumber: String) =
        Regex("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*\$").matches(phoneNumber)

    private fun validatePassword(password: String) =
        password.length > 2

    private fun validateUsername(usename: String) =
        usename.length > 2


    private suspend fun launchRegistration() {
        val name = readEntry("name")
        val email = readEntry("email", { validateEmail(it) })
        val phoneNumber = readEntry("phone number", { validatePhoneNumber(it) })
        val username = readEntry(
            "username",
            { validateUsername(it) },
            "Username must contain at least 3 characters"
        )
        val password = readEntry(
            "password",
            { validatePassword(it) },
            "Password must contain at least 3 characters"
        )
        client.register(
            RegisterRequest(
                name,
                email,
                phoneNumber,
                username,
                password
            )
        )
    }

    suspend fun runClient() {
        println("Welcome to SnailMail!")
        printHelp()
        while (isRunning) {
            waitForUser()
            val parts = readLine()?.split("\\s+".toRegex()) ?: break
            when (parts[0]) {
                "exit" -> {
                    isRunning = false
                }
                "register" -> {
                    launchRegistration()
                }
                else -> printHelp()
            }
        }
    }
}