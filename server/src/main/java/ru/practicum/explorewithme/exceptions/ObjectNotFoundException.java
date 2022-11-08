    package ru.practicum.explorewithme.exceptions;

    public class ObjectNotFoundException extends RuntimeException{
        public ObjectNotFoundException (String message, String errorObject) {
            super("The required object was not found." + message + " " + errorObject);
        }
    }
