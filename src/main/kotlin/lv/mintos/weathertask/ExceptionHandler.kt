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
    fun onServiceUnavailableException(exc: ServiceUnavailableException) =
            ExceptionResponseDTO(
                    errorCode = ExceptionCode.EXC_SERVICE_UNAVAILABLE,
                    message = "Oops... Could not access url:[" + exc.serviceUrl + "]"
            )

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun onException(exc: ServiceUnavailableException) =
            ExceptionResponseDTO(
                    errorCode = ExceptionCode.EXC_SYSTEM_EXCEPTION,
                    message = "Oops... Something went wrong. Please try again later!"
            )
}