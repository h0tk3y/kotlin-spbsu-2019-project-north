import dao.*
import databases.*
import org.koin.dsl.module

val daoModule = module {
    single<MessageDao> { MessageDB() }
    single<UserDao> { UserDB() }
    single<PersonalChatDao> { PersonalChatDB() }
    single<GroupChatDao> { GroupChatDB() }
    single<GroupChatsOfUserDao> { GroupChatOfUserDB() }
    single<BlockedUsersDao> { BlockedUsersDB() }
    single<MembersOfGroupChatDao> { MembersOfGroupChatDB() }
    single<ContactsOfUserDao> { ContactsOfUserDB() }
}