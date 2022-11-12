    package ru.practicum.explorewithme.exceptions;

    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseStatus;
    import java.time.LocalDateTime;
    import java.util.Collections;

    @ControllerAdvice
    public class ErrorHandler {

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(ObjectNotFoundException.class)
        public ApiError objectNotFoundResponse(ObjectNotFoundException e) {
            return new ApiError(Collections.emptyList(),
                    e.getMessage(),
                    "The required object was not found.",
                    StatusError.withIndex(404),
                    LocalDateTime.now());
        }

    }
