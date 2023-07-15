package br.ifnmg.edu.partyrent.shared.exceptions;

import java.time.OffsetDateTime;

public class BaseException extends RuntimeException {
    public Integer statusCode;
    public String message;
    public OffsetDateTime date;

    public BaseException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.date = OffsetDateTime.now();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }
}
