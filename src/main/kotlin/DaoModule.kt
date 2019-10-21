import org.koin.dsl.module

val daoModule = module {
    single<MessageDao> { MessageDB() }
    single<UserDao> { UserDB() }
    single<ChatDao> { ChatDB() }
}