import org.koin.dsl.module

val daoModule = module {
    single<MessageDao> {InMemoryMessageDB()}
    single<ChatMessageDao> {ChatMessages(get())}
}