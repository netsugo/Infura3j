package com.github.netsugo.infura3j

open class Header {
    val key: String
    val values: MutableList<String>

    constructor(key: String, value: String) {
        this.key = key
        this.values = mutableListOf(value)
    }

    constructor(key: String, values: MutableList<String>) {
        this.key = key
        this.values = values
    }

    fun toMap() = mapOf(key to values.joinToString(", "))
}
