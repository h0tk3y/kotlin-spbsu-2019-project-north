package model

import dao.Id
import dao.UserId

class Contact(
    val userId: UserId,
    val name: String
) {
    override fun equals(other: Any?): Boolean =
        (other as? Contact)?.userId == userId

    override fun hashCode(): Int = userId.hashCode()
}