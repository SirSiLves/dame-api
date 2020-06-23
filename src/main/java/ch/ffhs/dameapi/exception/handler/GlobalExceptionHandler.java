package ch.ffhs.dameapi.exception.handler;


import ch.ffhs.dameapi.exception.model.ApiException;
import ch.ffhs.dameapi.exception.model.ExceptionResponse;
import ch.ffhs.dameapi.exception.model.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This ExceptionHandler will process all thrown ApiException and will provide a proper error response as a json
     *
     *
     * @param exception a thrown {@link ApiException}
     * @return a json response with the status from the exception and the message
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> processApiException(ApiException exception) {
        return new ResponseEntity<>(exception.getExceptionResponse(), exception.getStatus());
    }

    /**
     * This ExceptionHandler will process all thrown NullPointerException
     *
     *
     * @param exception a thrown {@link NullPointerException}
     * @param request  the used WebRequest
     * @return a ResponseEntity<Object>
     */
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> processNullPointer(NullPointerException exception, WebRequest request) {
        String bodyOfResponse = "This request creates a NullPointer Exception";
        return handleExceptionInternal(exception, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request); //409
    }

    /**
     * This ExceptionHandler will process all thrown MethodNotAllowedException
     *
     *
     * @param exception a thrown {@link MethodNotAllowedException}
     * @param request  the used WebRequest
     * @return a ResponseEntity<Object>
     */
    @ExceptionHandler({MethodNotAllowedException.class})
    public ResponseEntity<Object> handleConflict(MethodNotAllowedException exception, WebRequest request) {
        String bodyOfResponse = "This method is not allowed";
        return handleExceptionInternal(exception, bodyOfResponse,
                new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request); //405
    }

    /**
     * This ExceptionHandler will process all thrown IllegalArgumentException
     *
     *
     * @param exception a thrown {@link IllegalArgumentException}
     * @param request  the used WebRequest
     * @return a ResponseEntity<Object>
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
        String bodyOfResponse = "Thisis a IllegalArgument or IllegalStatException";
        return handleExceptionInternal(exception, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request); //409
    }

    /**
     * Custom handling of Bind exception when validating and converting url parameters to usable
     * vars and objects in the controller functions.
     *
     * We relies on a proper use of the default message for the javax annotations, because the default messages aren't that helpful.
     *
     * @param exception the exception
     * @param headers   the headers to be written to the response
     * @param status    the selected response status
     * @param request   the current request
     * @return a {@link BindException} with the failure messages
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleBindException(@NonNull BindException exception,
                                                         @NonNull HttpHeaders headers,
                                                         @NonNull HttpStatus status,
                                                         @NonNull WebRequest request) {

        ValidationException validationException = ValidationException.from(exception);
        return new ResponseEntity<>(validationException.getExceptionResponse(), validationException.getStatus());
    }

    /**
     * Custom handling for MethodArgumentNotValidException (thrown when validation on an argument annotated with {@code @Valid} fails.)
     *
     *
     * @param exception the exception
     * @param headers   the headers to be written to the response
     * @param status    the selected response status
     * @param request   the current request
     * @return a {@link ValidationException} with the failure messages
     */
    @NonNull
    //@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException exception,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {

        ValidationException validationException = ValidationException.from(exception);
        return new ResponseEntity<>(validationException.getExceptionResponse(), validationException.getStatus());
    }

    /**
     * Custom handling for the HttpMessageNotReadableException as the default implementation would only deliver the
     * Http status without the reason. BAD REQUEST Could be the required request body is empty, missing headers or else,
     * without a proper error message.
     * See the {@link HttpMessageNotReadableException} usages for more details when it's used.
     *
     * So we convert it into our exception format and return it with the error message.
     *
     * @param ex the exception
     * @param headers   the headers to be written to the response
     * @param status    the selected response status
     * @param request   the current request
     * @return a {@link ApiException} response
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {

        ApiException exception = new ApiException(ex.getMessage(), status);
        return new ResponseEntity<>(exception.getExceptionResponse(), exception.getStatus());
    }
}


