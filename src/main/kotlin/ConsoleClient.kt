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
                "personal-message" -> {
                    sendMessage(isPersonal = true)
                }
                "chat-message" -> {
                    sendMessage(isPersonal = false)
                }
                "get-chats" -> {
                    getChats(isPersonal = null)
                }
                "get-personal-chats" -> {
                    getChats(isPersonal = true)
                }
                "get-group-chats" -> {
                    getChats(isPersonal = false)
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
                logout - Log out from site
                personal-message - Send a personal message
                chat-message - Send a message to a chat
                get-chats - Show IDs of all chats
                get-personal-chats - Show IDs of all personal chats
                get-group-chats - Show IDs of all group chats
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

    private fun readEmail(): String = readEntry("email",
        { Regex("[^@]+@[^\\.]+\\..+").matches(it) })

    private fun readPhoneNumber(): String = readEntry("phoneNumber",
        { Regex("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*\$").matches(it) })

    private fun readUsername(): String = readEntry(
        "username",
        { it.length > 2 },
        "Username must contain at least 3 characters"
    )

    private fun readPassword(): String = readEntry(
        "password",
        { it.length > 2 },
        "Password must contain at least 3 characters"
    )

    private fun readChatId(): Long = when (
        val id = readEntry("chat Id").toLongOrNull()) {
        null -> readChatId()
        else -> id
    }

    private fun readMessage() = readEntry("message", errorMsg = "Message should not be empty")

    private suspend fun launchRegistration() {
        val name = readEntry("name")
        val email = readEmail()
        val phoneNumber = readPhoneNumber()
        val username = readUsername()
        val password = readPassword()
        client.register(
            RegisterRequest(
                name,
                email,
                phoneNumber,
                username,
                password
            )
        )?.let { println("Error: $it") }
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

    private suspend fun sendMessage(isPersonal: Boolean) {
        val chatId = readChatId()
        val messageText = readMessage()
        client.sendMessage(SendMessageRequest(chatId, isPersonal, messageText))
    }

    private suspend fun getChats(isPersonal: Boolean?) {
        when (val chats = client.getChats(isPersonal)) {
            null -> println("Please log in first")
            else -> chats.forEach { println(it) }
        }
    }
}