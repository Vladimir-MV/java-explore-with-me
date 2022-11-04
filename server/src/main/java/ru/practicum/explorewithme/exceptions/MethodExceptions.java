    package ru.practicum.explorewithme.exceptions;

    public class MethodExceptions extends Exception{
        Integer status;
        String reason;

        public MethodExceptions (String message, Integer status, String reason) {
            super(message);
            this.status = status;
            this.reason = reason;
        }

    }
