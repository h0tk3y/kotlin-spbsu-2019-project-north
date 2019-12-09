class ConsoleClient(client: Client) {
    private val client = Client()
    private var isRunning = true
    private fun printHelp() {
        println("""
            Usage:
                exit: Quit the application
        """.trimIndent())
    }
    private fun runClient() {
        while (isRunning) {
            println("> ")
            val parts = readLine()?.split("\\s+".toRegex()) ?: break
            when (parts[0]) {
                "exit" -> {
                    isRunning = false
                }
                else -> printHelp()
            }
        }
    }
}