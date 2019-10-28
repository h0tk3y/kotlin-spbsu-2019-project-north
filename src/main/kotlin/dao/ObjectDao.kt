package dao

typealias Id = Long

interface ObjectDao<T> {
    fun getById(elemId: Id): T?
    fun addWithNewId(elem: T): Id
    fun deleteById(elemId: Id)
    val size: Int
}