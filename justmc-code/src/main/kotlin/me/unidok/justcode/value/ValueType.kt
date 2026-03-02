package me.unidok.justcode.value

enum class ValueType {
    ANY,
    NUMBER,
    TEXT,
    LOCATION,
    VECTOR,
    BLOCK,
    ITEM,
    SOUND,
    PARTICLE,
    POTION,
    ARRAY,
    MAP,
    VARIABLE;

    override fun toString(): String = name.lowercase()
}