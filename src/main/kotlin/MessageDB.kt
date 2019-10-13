object MessageDB : MessageDao {
    val base: MutableMap<MessageId, Message> = hashMapOf()

    override fun add(elem: Message): Id {
        val id = base.size.toLong()
        base.put(id, elem)
        return id
    }

    override fun modify(elemId: Id, newElem: Message) {
        base.put(elemId, newElem)
    }

    override fun get(elemId: Id): Message? {
        return base.get(elemId)
    }

    override fun delete(elemId: Id) {
        base.remove(elemId)
    }
}