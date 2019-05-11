package lv.mintos.weathertask.services

import lv.mintos.weathertask.services.api.WhatIsMyIpApiClient
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * Made based on following articles:
 * https://memorynotfound.com/client-ip-address-java/
 * https://www.oodlestechnologies.com/blogs/Java-Get-Client-IP-Address
 */
@Service
class IpAddressResolver(
        private val whatIsMyIpApi: WhatIsMyIpApiClient
) {
    companion object {
        const val UNKNOWN = "unknown"
        const val LOCALHOST_IP = "0:0:0:0:0:0:0:1"
        val IP_HEADER_CANDIDATES = listOf(
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

    fun of(request: HttpServletRequest): String {
        val ipFromHeaders = getIpFromHeaders(request)
        return if (ipFromHeaders != null) ipFromHeaders
        else getIpFromRemoteAddr(request)
    }

    private fun getIpFromRemoteAddr(request: HttpServletRequest): String =
            if (request.remoteAddr == LOCALHOST_IP) whatIsMyIpApi.getMachinePublicIp()
            else request.remoteAddr

    private fun getIpFromHeaders(request: HttpServletRequest): String? =
            IP_HEADER_CANDIDATES.map { request.getHeader(it) }
                    .filter(::isSpecified)
                    .firstOrNull(::notEqualsUnknown)

    private fun isSpecified(headerValue: String?) =
            !headerValue.isNullOrBlank()

    private fun notEqualsUnknown(headerValue: String) =
            headerValue.toLowerCase() != UNKNOWN
}