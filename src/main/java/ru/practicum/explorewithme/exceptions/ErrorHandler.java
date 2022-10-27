    package ru.practicum.explorewithme.exceptions;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.client.HttpClientErrorException;
    import org.springframework.web.client.HttpServerErrorException;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Collections;


    @ControllerAdvice
    public class ErrorHandler {

        @ExceptionHandler(MethodExceptions.class)
        public ResponseEntity<ApiError> exceptionResponse(String message, int status, String reason) {
            return ResponseEntity.status(status)
                    .body(new ApiError(Collections.emptyList(),
                          message,
                          reason,
                          StatusError.withIndex(status),
                          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
        @ExceptionHandler(HttpClientErrorException.Forbidden.class)
        public ResponseEntity<ApiError> handleException (HttpClientErrorException.Forbidden e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiError(Collections.emptyList(),
                          "Only pending or canceled events can be changed",
                          "For the requested operation the conditions are not met.",
                          StatusError.FORBIDDEN,
                          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }

        @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
        public ResponseEntity<ApiError> handleException (HttpServerErrorException.InternalServerError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(Collections.emptyList(),
                        "could not execute statement; SQL [n/a]; constraint [uq_category_name]; " +
                        "nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
                        "Error occurred",
                        StatusError.INTERNAL_SERVER_ERROR,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }

        @ExceptionHandler(HttpClientErrorException.Conflict.class)
        public ResponseEntity<ApiError> handleException (HttpClientErrorException.Conflict e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiError(Collections.emptyList(),
                         "could not execute statement; SQL [n/a]; constraint [uq_category_name]; " +
                         "nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
                         "Integrity constraint has been violated",
                         StatusError.CONFLICT,
                         LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }

    }
