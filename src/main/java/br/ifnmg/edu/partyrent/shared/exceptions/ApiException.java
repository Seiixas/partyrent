package br.ifnmg.edu.partyrent.shared.exceptions;

import java.time.OffsetDateTime;

public class ApiException {
    private Integer statusCode;
    private String message;
    private OffsetDateTime date;

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
