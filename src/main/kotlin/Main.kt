import org.koin.core.context.startKoin

fun main() {
    startKoin {
        printLogger()
        modules(daoModule)
    }
    val test = GroupChat(1, "kek", "link")
    test.addUser(42, "link")
    val msgId = test.messages.add(Message(13, "hello, world!", 42, 11, 1214))
    println(test.messages.get(msgId)!!.text)
}