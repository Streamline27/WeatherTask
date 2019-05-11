package lv.mintos.weathertask.exceptions

import java.lang.Exception

class ServiceUnavailableException(val serviceUrl: String) : Exception()