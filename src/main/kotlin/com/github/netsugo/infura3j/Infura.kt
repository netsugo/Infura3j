package com.github.netsugo.infura3j

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
    private fun authHeaderMap(projectSecret: String?, token68Jwt: String?) = run {
        val auth = Authorization()
        if (projectSecret != null) auth.addSecret(projectSecret)
        if (token68Jwt != null) auth.addJWT(token68Jwt)
        auth.toMap()
    }

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
    fun createHttpService(url: String, authorization: Authorization?) = run {
        val map = authorization?.toMap() ?: emptyMap()
        InfuraHttpService(url).apply { addHeaders(map) }
    }

    @JvmStatic
    fun createHttpService(network: String, projectId: String, projectSecret: String?, token68Jwt: String?) = run {
        val url = when {
            network.startsWith("polygon-") -> createPolygonHttpUrl(network, projectId)
            else -> createHttpUrl(network, projectId)
        }
        val map = authHeaderMap(projectSecret, token68Jwt)
        InfuraHttpService(url).apply { addHeaders(map) }
    }

    @JvmStatic
    fun createWsClient(uri: URI, authorization: Authorization?) = run {
        val map = authorization?.toMap() ?: emptyMap()
        WebSocketClient(uri, map)
    }

    @JvmStatic
    fun createWsClient(network: String, projectId: String, projectSecret: String?, token68Jwt: String?) = run {
        val url = createWsUrl(network, projectId)
        val uri = URI.create(url)
        val map = authHeaderMap(projectSecret, token68Jwt)
        WebSocketClient(uri, map)
    }

    @JvmStatic
    fun createWsService(uri: URI, authorization: Authorization?) = run {
        val client = createWsClient(uri, authorization)
        WebSocketService(client, false)
    }

    @JvmStatic
    fun createWsService(network: String, projectId: String, projectSecret: String?, token68Jwt: String?) = run {
        val client = createWsClient(network, projectId, projectSecret, token68Jwt)
        WebSocketService(client, false)
    }
}
