package br.ifnmg.edu.partyrent.shared.handlers;

import br.ifnmg.edu.partyrent.modules.users.exceptions.UserAlreadyExistsException;
import br.ifnmg.edu.partyrent.shared.exceptions.ApiException;
import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleException(BaseException ex, WebRequest request) {
        Integer statusCode = ex.getStatusCode();
        String message = ex.getMessage();
        OffsetDateTime date = ex.getDate();

        ApiException exception = new ApiException();
        exception.setDate(date);
        exception.setMessage(message);
        exception.setStatusCode(statusCode);

        return handleExceptionInternal(
                ex,
                exception,
                new HttpHeaders(),
                HttpStatusCode.valueOf(statusCode),
                request
        );
    }

}
