    package ru.practicum.explorewithme.exceptions;

    import org.springframework.core.convert.ConversionFailedException;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseStatus;
    import org.springframework.web.bind.annotation.RestControllerAdvice;
    import javax.validation.ConstraintViolationException;
    import java.time.LocalDateTime;
    import java.util.Collections;


    @RestControllerAdvice
    public class ErrorHandler {


        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler
        public ApiError requestErrorResponse(RequestErrorException e) {
            return new ApiError(Collections.emptyList(),
                          e.getMessage(),
                    "For the requested operation the conditions are not met.",
                          StatusError.withIndex(400),
                          LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler
        public ApiError requestBadMethodArgumentError(MethodArgumentNotValidException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "For the requested operation the conditions are not met.",
                    StatusError.withIndex(400),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler
        public ApiError requestConversionFailedResponse(ConversionFailedException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "For the requested operation the conditions are not met.",
                    StatusError.withIndex(400),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler
        public ApiError requestErrorResponse(ConstraintViolationException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "For the requested operation the conditions are not met.",
                    StatusError.withIndex(400),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @ExceptionHandler
        public ApiError errorResponse(ConditionsOperationNotMetException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "For the requested operation the conditions are not met.",
                    StatusError.withIndex(403),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler
        public ApiError objectNotFoundResponse(ObjectNotFoundException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "The required object was not found.",
                    StatusError.withIndex(404),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler
        public ApiError dataIntegrityViolationResponse(DataIntegrityViolationException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "Integrity constraint has been violated",
                    StatusError.withIndex(409),
                    LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ExceptionHandler
        public ApiError exceptionResponse(InternalServerErrorException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "Error occurred",
                    StatusError.withIndex(500),
                    LocalDateTime.now());
        }
    }
