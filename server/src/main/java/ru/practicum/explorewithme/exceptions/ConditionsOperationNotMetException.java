    package ru.practicum.explorewithme.exceptions;

    public class ConditionsOperationNotMetException extends RuntimeException{
        public ConditionsOperationNotMetException(String message, String errorObject) {
            super("Only pending or canceled events can be changed." + message + " " + errorObject);
        }

    }
