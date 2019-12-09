package entries

import PersonalChat
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.PersonalChats

class PersonalChatDBEnrty(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PersonalChatDBEnrty>(PersonalChats)

    var member1 by PersonalChats.member1
    var member2 by PersonalChats.member2

    fun toPersonalChat() = PersonalChat(id.value, member1.value, member2.value)
}