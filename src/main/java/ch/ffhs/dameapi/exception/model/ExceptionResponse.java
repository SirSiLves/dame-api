package ch.ffhs.dameapi.exception.model;


import java.time.LocalDateTime;


public class ExceptionResponse {

    private final String message;
    private final String debugMessage;
    private final Integer status;
    private final String code;
    private final LocalDateTime timeStamp;


    public ExceptionResponse(String message, String debugMessage, Integer status, String code, LocalDateTime timeStamp) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.status = status;
        this.code = code;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
