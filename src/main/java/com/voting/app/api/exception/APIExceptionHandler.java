package com.voting.app.api.exception;

import com.voting.app.domain.exception.EntidadeNaoEncontradaException;
import com.voting.app.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    private static final int BAD_REQUEST_CODE = HttpStatus.BAD_REQUEST.value();

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        int notFoundCode = HttpStatus.NOT_FOUND.value();
        APIError error = createError(notFoundCode, "Entidade não encontrada", ex.getMessage())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatusCode.valueOf(notFoundCode), request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handlePautaNaoAbertaException(NegocioException ex, WebRequest request) {
        APIError error = createError(BAD_REQUEST_CODE, "Violação de regra de negócio", ex.getMessage())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatusCode.valueOf(BAD_REQUEST_CODE), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {

        if (body == null) {
            body = APIError.builder()
                    .title(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                    .status(statusCode.value())
                    .build();
        } else if (body instanceof String) {
            body = APIError.builder()
                    .title((String) body)
                    .status(statusCode.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private APIError.APIErrorBuilder createError(Integer status, String title,String detail) {
        return APIError.builder()
                .status(status)
                .title(title)
                .detail(detail);
    }
}
