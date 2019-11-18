package tables

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.datetime

object Messages : LongIdTable() {
    val from = long("from").entityId().references(Users.id)
    val typeOfChat = bool("type of chat")
    val chat = long("chat")
    val text = text("text")
    val dateTime = datetime("dateTime")
    val isDeleted = bool("isDeleted")
    val isEdited = bool("isEdited")
}