import org.koin.dsl.module

val daoModule = module {
    single<MessageDao> {MessageDB()}
    single<ChatMessageDao> {ChatMessages(get())}
}