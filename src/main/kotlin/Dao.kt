typealias Id = Long

interface Dao<T> {
    fun get(elemId: Id): T?
    fun add(elem: T): Id
    fun modify(elemId: Id, newElem: T)
    fun delete(elemId: Id)
}