package lv.mintos.weathertask.services

import io.mockk.every
import io.mockk.mockk
import lv.mintos.weathertask.TestData.IP_ADDRESS
import lv.mintos.weathertask.TestData.IP_ADDRESS_LOCALHOST
import lv.mintos.weathertask.services.clients.WhatIsMyIpApiClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import javax.servlet.http.HttpServletRequest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IpAddressExtractorTest {

    private val whatIsMyIpApiClient: WhatIsMyIpApiClient = mockk()
    private val ipAddressExtractor = IpAddressExtractor(whatIsMyIpApiClient)

    @BeforeAll
    fun setUp() {
        every { whatIsMyIpApiClient.getMachinePublicIp() } returns IP_ADDRESS
    }

    @Test
    fun `given ip is not present in headers and remoteAddr is localhost machine public ip address is returned`() {
        val request: HttpServletRequest = mockk()
        every { request.getHeader(any()) }.returns(null)
        every { request.remoteAddr }.returns(IP_ADDRESS_LOCALHOST)
        val ip = ipAddressExtractor.getIpBy(request)
        assertThat(ip).isEqualTo(IP_ADDRESS)
    }

    @Test
    fun `given ip is not present in headers and remoteAddr is not localhost remoteAddr is returned`() {
        val request: HttpServletRequest = mockk()
        every { request.getHeader(any()) }.returns(null)
        every { request.remoteAddr }.returns(IP_ADDRESS)
        val ip = ipAddressExtractor.getIpBy(request)
        assertThat(ip).isEqualTo(IP_ADDRESS)
    }

    @ParameterizedTest
    @MethodSource("ipHeaders")
    fun `given ip is in header and other header values are null, ip is returned from that header`(header: String) {
        val request: HttpServletRequest = mockk()
        every { request.getHeader(not(header)) } returns(null)
        every { request.getHeader(header) } returns(IP_ADDRESS)
        val ip = ipAddressExtractor.getIpBy(request)
        assertThat(ip).isEqualTo(IP_ADDRESS)
    }

    @ParameterizedTest
    @MethodSource("ipHeaders")
    fun `given ip is in header and other header values are unknown , ip is returned from that header`(header: String) {
        val request: HttpServletRequest = mockk()
        every { request.getHeader(not(header)) } returns("unknown")
        every { request.getHeader(header) } returns(IP_ADDRESS)
        val ip = ipAddressExtractor.getIpBy(request)
        assertThat(ip).isEqualTo(IP_ADDRESS)
    }

    @ParameterizedTest
    @MethodSource("ipHeaders")
    fun `given ip is in header and other header values are UNKNOWN, ip is returned from that header`(header: String) {
        val request: HttpServletRequest = mockk()
        every { request.getHeader(not(header)) } returns("UNKNOWN")
        every { request.getHeader(header) } returns(IP_ADDRESS)
        val ip = ipAddressExtractor.getIpBy(request)
        assertThat(ip).isEqualTo(IP_ADDRESS)
    }

    private fun ipHeaders() = Stream.of(
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