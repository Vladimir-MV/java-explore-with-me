    package ru.practicum.explorewithme.exceptions;

    public class ObjectNotFoundException extends Exception {


        public ObjectNotFoundException(String message, String errorObject) {

            super("The required object was not found." + message + " " + errorObject);
        }
    }
