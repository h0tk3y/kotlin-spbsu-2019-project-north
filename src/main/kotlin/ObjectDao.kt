typealias Id = Long

interface ObjectDao<T> {
    fun getById(elemId: Id): T?
    fun addWithNewId(elem: T): Id
    fun modifyById(elemId: Id, newElem: T)
    fun deleteById(elemId: Id)
    val size: Int
}