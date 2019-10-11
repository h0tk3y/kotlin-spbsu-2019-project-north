class InMemoryMessageDB : MessageDao {

    private val messagesById: MutableMap<Id, Message> = mutableMapOf()
    private var currentId: Id = 0

    override fun get(elemId: Id): Message? {
        return messagesById[elemId]
    }

    override fun add(elem: Message): Id {
        return (++currentId).also { messagesById[it] = elem }
    }

    override fun modify(elemId: Id, newElem: Message) {
        messagesById[elemId] = newElem
    }

    override fun delete(elemId: Id) {
        messagesById.remove(elemId)
    }
}