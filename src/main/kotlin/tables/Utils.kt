package tables

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance

fun getEntityIDImpl(id: Long, type: KClass<out LongEntity>): EntityID<Long>? =
    (type.companionObjectInstance as? LongEntityClass<*>)?.findById(id)?.id

inline fun <reified T : LongEntity> getEntityID(id: Long) = getEntityIDImpl(id, T::class)