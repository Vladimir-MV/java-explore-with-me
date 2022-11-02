    package ru.practicum.explorewithme.exceptions;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseStatus;
    import org.springframework.web.client.HttpClientErrorException;
    import org.springframework.web.client.HttpServerErrorException;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Collections;


    @ControllerAdvice
    public class ErrorHandler {
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(RequestErrorException.class)
        public ApiError requestErrorResponse() {
            return new ApiError(Collections.emptyList(),
                    "Only pending or canceled events can be changed",
                    "For the requested operation the conditions are not met.",
                          StatusError.withIndex(400),
                          LocalDateTime.now());
        }

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @ExceptionHandler(ConditionsOperationNotMetException.class)
        public ApiError errorResponse() {
            return new ApiError(Collections.emptyList(),
                    "Only pending or canceled events can be changed",
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

//        @ExceptionHandler(MethodArgumentNotValidException.class)
//        @ResponseStatus(HttpStatus.BAD_REQUEST)
//        public ApiError handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//            final List<FieldError> errors = e.getBindingResult().getFieldErrors();
//            StringBuilder sb = new StringBuilder();
//            for (FieldError error : errors) {
//                sb.append(error.getField()).append(" ").append(error.getDefaultMessage());
//            }
//            ApiError apiError = ApiError.builder()
//                    .errors(List.of())
//                    .message(sb.toString())
//                    .reason("Ошибка валидации аргументов")
//                    .status(HttpStatus.BAD_REQUEST)
//                    .build();
//            apiError.setEventDate(LocalDateTime.now());
//            return apiError;
//
//        }

//        @ResponseStatus(HttpStatus.BAD_REQUEST)
//        @ExceptionHandler(MethodExceptions.class)
//        public ApiError exceptionResponse(MethodExceptions e) {
//            return new ApiError(Collections.emptyList(),
//                    e.getMessage(),
//                    "The required object was not found.",
//                    StatusError.withIndex(400),
//                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        }

//        @ExceptionHandler(HttpClientErrorException.BadRequest.class)
//        public ResponseEntity handleBadRequestException () {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ApiError(Collections.emptyList(),
//                            "Only pending or canceled events can be changed",
//                            "For the requested operation the conditions are not met.",
//                            StatusError.BAD_REQUEST,
//                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
//        }
//        @ExceptionHandler(HttpClientErrorException.Forbidden.class)
//        public ResponseEntity handleForbiddenException () {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(new ApiError(Collections.emptyList(),
//                          "Only pending or canceled events can be changed",
//                          "For the requested operation the conditions are not met.",
//                          StatusError.FORBIDDEN,
//                          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
//        }
////        ResponseEntity.status(HttpStatus.FORBIDDEN)
////                .body(
//
//        @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
//        public ResponseEntity handleServerErrorException () {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ApiError(Collections.emptyList(),
//                        "could not execute statement; SQL [n/a]; constraint [uq_category_name]; " +
//                        "nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
//                        "Error occurred",
//                        StatusError.INTERNAL_SERVER_ERROR,
//                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
//        }
////        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                .body(
//
//        @ExceptionHandler(HttpClientErrorException.Conflict.class)
//        public ResponseEntity handleConflictException () {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                .body(new ApiError(Collections.emptyList(),
//                         "could not execute statement; SQL [n/a]; constraint [uq_category_name]; " +
//                         "nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
//                         "Integrity constraint has been violated",
//                         StatusError.CONFLICT,
//                         LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
//        }

    }
