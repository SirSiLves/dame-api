package ch.ffhs.dameapi.exception.model;


import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;


public class ApiException extends RuntimeException {

    private final String message;
    private final String debugMessage;
    private final HttpStatus status;
    private final LocalDateTime timeStamp;

    public ApiException(String message, HttpStatus status) {
        this.message = message;
        this.debugMessage = "";
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }

    public ApiException(String message, HttpStatus status, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }


    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    /**
     * This function will create a ExceptionResponse
     *
     * @return a ExceptionResponse
     */
    public ExceptionResponse getExceptionResponse() {
        return new ExceptionResponse(getMessage(), getDebugMessage(), getStatus().value(), getStatus().getReasonPhrase(), getTimeStamp());
    }
}

