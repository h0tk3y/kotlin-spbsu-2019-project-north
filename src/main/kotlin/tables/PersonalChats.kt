package tables

import org.jetbrains.exposed.dao.LongIdTable

object PersonalChats : LongIdTable() {
    val member1 = long("member1")
    val member2 = long("member2")
}