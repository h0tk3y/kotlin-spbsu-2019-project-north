package model

import dao.Id

class Contact(
    val id: Id,
    val name: String
) {
    override fun equals(other: Any?): Boolean =
        (other as? Contact)?.id == id

    override fun hashCode(): Int = id.hashCode()
}