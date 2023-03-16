package com.generic.khatabook.exceptions;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;
    private String messages;
    private String details;

    public ExceptionResponse(Date timestamp, String messages, String details) {
        this.timestamp = timestamp;
        this.messages = messages;
        this.details = details;
    }

    public ExceptionResponse() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "timestamp=" + timestamp +
                ", messages='" + messages + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
