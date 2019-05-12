package lv.mintos.weathertask.services

import lv.mintos.weathertask.services.clients.WhatIsMyIpApiClient
import org.springframework.stereotype.Service
import java.net.InetAddress
import javax.servlet.http.HttpServletRequest

/**
 * Made based on following articles:
 * https://memorynotfound.com/client-ip-address-java/
 * https://www.oodlestechnologies.com/blogs/Java-Get-Client-IP-Address
 */
@Service
class IpAddressExtractor(
        private val whatIsMyIpApi: WhatIsMyIpApiClient
) {
    companion object {
        private const val HEADER_VALUE_UNKNOWN = "unknown"
        private val ipHeaderCandidates = listOf(
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        )
    }

    fun getIpBy(request: HttpServletRequest): String {
        val ipFromHeaders = getIpFromHeaders(request)
        return if (ipFromHeaders == null) getIpFromRemoteAddress(request)
        else ipFromHeaders
    }

    private fun getIpFromRemoteAddress(request: HttpServletRequest): String {
        val ip = request.remoteAddr
        val address = InetAddress.getByName(ip)
        return if (address.isLoopbackAddress || address.isSiteLocalAddress) whatIsMyIpApi.getMachinePublicIp()
        else ip
    }

    private fun getIpFromHeaders(request: HttpServletRequest): String? =
            ipHeaderCandidates.map { request.getHeader(it) }
                    .filter(::isSpecified)
                    .firstOrNull(::notEqualsUnknown)

    private fun isSpecified(headerValue: String?) =
            !headerValue.isNullOrBlank()

    private fun notEqualsUnknown(headerValue: String) =
            headerValue.toLowerCase() != HEADER_VALUE_UNKNOWN
}