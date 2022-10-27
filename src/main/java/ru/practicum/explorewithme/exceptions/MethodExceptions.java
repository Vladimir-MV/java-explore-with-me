package ru.practicum.explorewithme.exceptions;

public class MethodExceptions extends Exception{
    private int status;
    private String reason;

    public MethodExceptions (final String message, int status, String reason) {
        super(message);
        this.status = status;
        this.reason = reason;
    }
}
