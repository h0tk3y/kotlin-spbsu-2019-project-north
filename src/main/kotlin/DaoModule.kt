import org.koin.dsl.module

val daoModule = module {
    single<MessageDao> { MessageDB() }
    single<UserDao> { UserDB() }
    single<ChatDao> { ChatDB() }
    single<ChatMessageDao> { ChatMessages(get()) }
}