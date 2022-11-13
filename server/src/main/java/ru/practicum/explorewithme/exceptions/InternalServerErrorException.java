    package ru.practicum.explorewithme.exceptions;

    public class InternalServerErrorException extends RuntimeException {
        public InternalServerErrorException(String message, String errorObject) {

            super("could not execute statement; SQL [n/a]; constraint [uq_category_name]; " + "" +
                    "nested exception is org.hibernate.exception.ConstraintViolationException: " +
                    "could not execute statement" + message + " " + errorObject);
        }

    }
