    package ru.practicum.explorewithme.exceptions;

    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseStatus;
    import java.time.LocalDateTime;
    import java.util.Collections;


    @ControllerAdvice
    public class ErrorHandler {
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(RequestErrorException.class)
        public ApiError requestErrorResponse(RequestErrorException e) {
            return new ApiError(Collections.emptyList(),
                          e.getMessage(),
                    "For the requested operation the conditions are not met.",
                          StatusError.withIndex(400),
                          LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @ExceptionHandler(ConditionsOperationNotMetException.class)
        public ApiError errorResponse(ConditionsOperationNotMetException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "For the requested operation the conditions are not met.",
                    StatusError.withIndex(403),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(ObjectNotFoundException.class)
        public ApiError objectNotFoundResponse(ObjectNotFoundException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "The required object was not found.",
                    StatusError.withIndex(404),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ApiError dataIntegrityViolationResponse(DataIntegrityViolationException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "Integrity constraint has been violated",
                    StatusError.withIndex(409),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ExceptionHandler(InternalServerErrorException.class)
        public ApiError exceptionResponse(InternalServerErrorException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "Error occurred",
                    StatusError.withIndex(500),
                    LocalDateTime.now());
        }

    }
