package databases

import dao.Id
import dao.PersonalChatDao
import dao.UserId
import model.PersonalChat
import org.jetbrains.exposed.sql.transactions.transaction

class PersonalChatDB : PersonalChatDao {
    override fun addNewPersonalChat(member1: UserId, member2: UserId) =
        transaction {
            PersonalChat.new {
                this.member1 = member1
                this.member2 = member2
            }
        }

    override fun getById(elemId: Id) =
        transaction { PersonalChat.findById(elemId) }

    override fun deleteById(elemId: Id) =
        transaction { PersonalChat.findById(elemId)?.delete() ?: Unit }

    override val size: Int
        get() = transaction { PersonalChat.all().count() }
}