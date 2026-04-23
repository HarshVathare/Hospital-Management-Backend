package com.withHarsh.MediCore.Error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.relation.RoleInfoNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Apierror> userNotfondException(UsernameNotFoundException ex) {
        Apierror apierror = new Apierror("User not found by Username : " + ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apierror, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Apierror> HandleAuthenticationException(AuthenticationException ex) {
        Apierror apierror = new Apierror("Authentication Failed ..! " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apierror, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Apierror> JwtExceptionhandler(JwtException ex) {
        Apierror apierror = new Apierror("Invalid Jwt Token" + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apierror, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Apierror> handleAccessDeniedException(AccessDeniedException ex) {
        Apierror apierror = new Apierror("Access Denied : Insufficient Permissions ..!" + ex.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apierror, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Apierror> handleException(Exception ex) {
        Apierror apierror = new Apierror("An Unaccepted error Occurs ..!" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apierror, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoleInfoNotFoundException.class)
    public ResponseEntity<Apierror> HandleRolleType(RoleInfoNotFoundException ex) {
        Apierror apierror = new Apierror("RollType is Different Please Check The User Role ..! "+ex.getMessage(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apierror, HttpStatus.UNAUTHORIZED);
    }
}