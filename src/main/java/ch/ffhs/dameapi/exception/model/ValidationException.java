package ch.ffhs.dameapi.exception.model;


import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class ValidationException extends ApiException {

    private final Map<String, String> errors;

    public ValidationException(String message, Map<String, String> errors) {
        super(message, HttpStatus.BAD_REQUEST);
        this.errors = errors;
    }

    public ValidationException(Map<String, String> errors) {
        super("The validation failed, check the errors for more details", HttpStatus.BAD_REQUEST);
        this.errors = errors;
    }


    private Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Create a ValidationException from a BindException
     *
     *
     * @param exception the BindException
     * @return a ValidationException
     */
    public static ValidationException from(BindException exception) {
        return new ValidationException(collectErrorMessages(exception.getAllErrors()));
    }

    /**
     * Create a ValidationException from a MethodArgumentNotValidException
     *
     *
     * @param exception the MethodArgumentNotValidException
     * @return a ValidationException
     */
    public static ValidationException from(MethodArgumentNotValidException exception) {
        return new ValidationException(collectErrorMessages(exception.getBindingResult().getAllErrors()));
    }

    /**
     * With this method we extract the error messages from a BindResult and map them to a pair field name & message or
     * if the error isn't a FieldError to a pair "general" & message
     *
     *
     * @param errors the ObjectError list from a BindResult object
     * @return a map with the error messages for all exceptions in the BindResult
     */
    private static Map<String, String> collectErrorMessages(List<ObjectError> errors) {
        return errors.stream().map(objectError -> {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                return Map.entry(fieldError.getField(), Objects.requireNonNullElse(
                        objectError.getDefaultMessage(),
                        "There isn't a default error message defined. Something you entered was wrong " +
                                "check your spelling or consult the api spec for more information about the endpoint"));
            }

            return Map.entry("general", Objects.requireNonNullElse(
                    objectError.getDefaultMessage(),
                    "There isn't a default error message defined. Something you entered was wrong " +
                            "check your spelling or consult the api spec for more information about the endpoint"));
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public ExceptionResponse getExceptionResponse() {
        return new ValidationExceptionResponse(getMessage(), getDebugMessage(), getStatus(), getTimeStamp(), getErrors());
    }

    private static class ValidationExceptionResponse extends ExceptionResponse {
        private ValidationExceptionResponse(String message, String debugMessage, HttpStatus status,
                                            LocalDateTime timestamp, Map<String, String> errors) {
            super(message, debugMessage, status.value(), status.getReasonPhrase(), timestamp);
            this.errors = errors;
        }

        private Map<String, String> errors;
    }
}
