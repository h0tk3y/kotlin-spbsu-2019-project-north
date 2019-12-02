package tables

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.datetime

object Messages : LongIdTable() {
    val from = long("from").entityId().references(Users.id)
    val isPersonal = bool("isPersonal").index()
    val chat = long("chat").index()
    val text = text("text").index()
    val dateTime = datetime("dateTime")
    val isDeleted = bool("isDeleted")
    val isEdited = bool("isEdited")
}