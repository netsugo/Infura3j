package com.github.netsugo.infura3j

import java.util.*
import kotlin.collections.HashMap

class HeadersBuilder {
    private val headers = HashMap<String, String>()

    fun put(key: String, value: String) = kotlin.run {
        headers[key] = value
        this
    }

    fun putAll(headers: Map<String, String>) = kotlin.run {
        this.headers.putAll(headers)
        this
    }

    fun putProjectSecret(apiSecret: String) = kotlin.run {
        val encoder = Base64.getEncoder()
        val rawHeaderValue = ":${apiSecret}"
        val encoded = encoder.encodeToString(rawHeaderValue.toByteArray())
        put("Authorization", "Basic $encoded")
    }

    fun build() = headers
}
