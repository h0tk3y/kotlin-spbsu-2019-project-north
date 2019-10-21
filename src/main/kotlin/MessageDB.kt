class MessageDB : MessageDao {
    private val base: MutableMap<MessageId, Message> = hashMapOf()

    override fun addWithNewId(elem: Message): Id {
        val id = base.size.toLong()
        base[id] = elem
        return id
    }

    override fun modifyById(elemId: Id, newElem: Message) {
        base[elemId] = newElem
    }

    override fun getById(elemId: Id): Message? {
        return base[elemId]
    }

    override fun deleteById(elemId: Id) {
        base.remove(elemId)
    }

    override val size
        get() = base.size
}