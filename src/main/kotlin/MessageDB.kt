object MessageDB : MessageDao {
    private val base: MutableMap<MessageId, Message> = hashMapOf()

    override fun add(elem: Message): Id {
        val id = base.size.toLong()
        base[id] = elem
        return id
    }

    override fun modify(elemId: Id, newElem: Message) {
        base[elemId] = newElem
    }

    override fun get(elemId: Id): Message? {
        return base[elemId]
    }

    override fun delete(elemId: Id) {
        base.remove(elemId)
    }

    override val size
        get() = base.size
}