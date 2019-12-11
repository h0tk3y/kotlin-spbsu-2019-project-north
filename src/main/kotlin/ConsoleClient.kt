suspend fun main() {
    ConsoleClient().runClient()
}

class ConsoleClient() {
    private val client = Client()
    private var isRunning = true

    suspend fun runClient() {
        println("Welcome to SnailMail!")
        printHelp()
        while (isRunning) {
            waitForUser()
            val parts = readLine()?.split("\\s+".toRegex()) ?: break
            when (parts[0]) {
                "register" -> {
                    launchRegistration()
                }
                "login" -> {
                    launchAuthorization()
                }
                "logout" -> {
                    logOut()
                }
                "exit" -> {
                    isRunning = false
                }
                else -> printHelp()
            }
        }
    }

    private fun waitForUser() = print("> ")

    private fun printHelp() {
        println(
            """
            Usage:
                register - Register a new user
                login - Log in as an existing user
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

    private fun readEmail() : String =  readEntry("email",
        { Regex("[^@]+@[^\\.]+\\..+").matches(it) })

    private fun readPhoneNumber() : String =  readEntry("phoneNumber",
        { Regex("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*\$").matches(it) })

    private fun readUsername() : String =  readEntry("username",
        { it.length > 2 },
        "Username must contain at least 3 characters")

    private fun readPassword() : String =  readEntry("password",
        { it.length > 2 },
        "Password must contain at least 3 characters")

    private suspend fun launchRegistration() {
        val name = readEntry("name")
        val email = readEmail()
        val phoneNumber = readPhoneNumber()
        val username = readUsername()
        val password = readPassword()
        try {
            client.register(
                RegisterRequest(
                    name,
                    email,
                    phoneNumber,
                    username,
                    password
                )
            )
        } catch (e: InvalidRequestException) {
            println("Error: ${e.reason}")
        }
    }

    private suspend fun launchAuthorization() {
        val login = readUsername()
        val password = readPassword()
        when (val errorMsg = client.login(LoginRequest(login, password))) {
            null -> println("Logged in as $login")
            else -> println("Error: $errorMsg")
        }
    }

    private fun logOut() = client.logout()
}