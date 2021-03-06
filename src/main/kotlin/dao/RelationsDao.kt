package dao

interface RelationsDao<X, Y> {
    fun add(key: X, value: Y): Boolean
    fun remove(key: X, value: Y): Boolean
    fun select(key: X): List<Y>
    fun contains(key : X, value : Y) : Boolean
}