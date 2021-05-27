package com.github.netsugo.infura3j

import java.util.*

open class Authorization : Header {
    constructor(headerValues: MutableList<String>) : super("Authorization", headerValues)
    constructor(headerValue: String) : this(mutableListOf(headerValue))
    constructor() : this(mutableListOf())

    fun add(type: String, credentials: String) = run {
        values.add("$type $credentials")
        this
    }

    fun addSecret(projectSecret: String) = run {
        val encoder = Base64.getEncoder()
        val raw = ":$projectSecret"
        val binary = encoder.encode(raw.encodeToByteArray())
        val cred = String(binary)
        add("Basic", cred)
    }

    fun addJWT(token68: String) = add("Bearer", token68)
}
