typealias Id = Long

interface Dao<T> {
    fun getById(elemId: Id): T?
    fun addWithNewId(elem: T): Id
    fun modifyById(elemId: Id, newElem: T)
    fun deleteById(elemId: Id)
    val size: Int
}