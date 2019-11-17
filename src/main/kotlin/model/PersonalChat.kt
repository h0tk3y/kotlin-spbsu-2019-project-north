package model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.PersonalChats

class PersonalChat(id: EntityID<Long>) : LongEntity(id), Chat {
    companion object : LongEntityClass<PersonalChat>(PersonalChats)

    var member1 by PersonalChats.member1
    var member2 by PersonalChats.member2
}