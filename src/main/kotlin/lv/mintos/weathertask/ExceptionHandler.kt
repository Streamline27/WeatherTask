package lv.mintos.weathertask

import lv.mintos.weathertask.dto.ExceptionResponseDTO
import lv.mintos.weathertask.exceptions.ExceptionCode
import lv.mintos.weathertask.exceptions.ServiceUnavailableException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(value = [ServiceUnavailableException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun onStateException(exc: ServiceUnavailableException) =
            ExceptionResponseDTO(
                    errorCode = ExceptionCode.EXC_SERVICE_UNAVAILABLE,
                    message = "Ooops... Could not access url:[" + exc.serviceUrl + "]"
            )
}