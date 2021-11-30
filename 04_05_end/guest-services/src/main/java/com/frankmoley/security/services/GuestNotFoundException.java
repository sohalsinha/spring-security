package com.frankmoley.security.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Frank P. Moley III.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException(String s) {
        super(s);
    }
}
