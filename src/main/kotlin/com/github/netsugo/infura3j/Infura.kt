package com.github.netsugo.infura3j

import org.web3j.protocol.http.HttpService
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.protocol.websocket.WebSocketClient
import org.web3j.protocol.websocket.WebSocketService

import java.net.URI

object Infura {
    const val Mainnet = "mainnet"
    const val Ropsten = "ropsten"
    const val Rinkeby = "rinkeby"
    const val Kovan = "kovan"
    const val Goerli = "goerli"
    const val PolygonMainnet = "polygon-mainnet"
    const val PolygonMumbai = "polygon-mumbai"

    @JvmStatic
    fun createHttpUrl(network: String, projectId: String) =
        "https://${network}.infura.io/v3/${projectId}"

    @JvmStatic
    fun createPolygonHttpUrl(polygonNetwork: String, projectId: String) =
        "https://${polygonNetwork}.infura.io/${projectId}"

    @JvmStatic
    fun createWsUrl(network: String, projectId: String) =
        "wss://${network}.infura.io/ws/v3/${projectId}"

    @JvmStatic
    fun createHttpService(url: String, projectSecret: String?): HttpService = run {
        val headers = HeadersBuilder().apply {
            projectSecret?.let { putProjectSecret(it) }
        }.build()
        InfuraHttpService(url).apply { addHeaders(headers) }
    }

    @JvmStatic
    fun createWsService(url: String, projectSecret: String?) = run {
        val uri = URI.create(url)
        val headers = HeadersBuilder().apply {
            projectSecret?.let { putProjectSecret(it) }
        }.build()
        val client = WebSocketClient(uri, headers)
        WebSocketService(client, false)
    }
}
