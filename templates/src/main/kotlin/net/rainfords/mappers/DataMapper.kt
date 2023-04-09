package net.rainfords.mappers

interface DataMapper<T> {
    fun setData(payload: T): Map<String, Any?>
}