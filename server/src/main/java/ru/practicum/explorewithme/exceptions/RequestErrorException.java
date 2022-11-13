    package ru.practicum.explorewithme.exceptions;

    public class RequestErrorException extends RuntimeException {

        public RequestErrorException(String message, String errorObject) {

            super("Only pending or canceled events can be changed." + message + " " + errorObject);
        }


    }
