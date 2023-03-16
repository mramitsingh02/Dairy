package com.generic.khatabook.exceptions;

import com.generic.khatabook.entity.AppEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not fount.")
public class NotFoundException extends RuntimeException {

    public NotFoundException(final AppEntity appEntity, final String id) {
        super(appEntity.getName() + " " + id + " not fount!.");
    }

    public NotFoundException(final AppEntity appEntity, final Long id) {
        this(appEntity, String.valueOf(id));
    }

    public ProblemDetail get(){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, super.getMessage());
    }


}
