package com.agendzy.api.entrypoint.http.exception;

import com.agendzy.api.core.exception.*;
import com.agendzy.api.entrypoint.http.exception.formatter.HttpErrorMessageFormatter;
import com.agendzy.api.entrypoint.http.exception.message.HttpErrorMessage;
import com.agendzy.api.entrypoint.http.exception.response.HttpErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    // Método relacionado a exceções não mapeadas
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpErrorResponse exceptionRestResponse = buildErrorResponse(status,
                HttpErrorMessage.SYSTEM_ERROR, HttpErrorMessage.INTERNAL_SERVER_ERROR).build();
        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    // Método relacionado a exceções de campos estão inválidos
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatusCode status,
                                                               WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();

        List<HttpErrorResponse.FieldError> fields = bindingResult.getFieldErrors()
                .stream()
                .map(campoComErro -> HttpErrorResponse.FieldError.builder()
                        .field(campoComErro.getField())
                        .message(campoComErro.getDefaultMessage())
                        .build())
                .toList();

        HttpErrorResponse exceptionRestResponse =
                buildErrorResponse(status, HttpErrorMessage.INVALID_DATA, HttpErrorMessage.INVALID_FIELDS)
                        .errors(fields)
                        .build();

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }


    // Método relacionado a exceções de mensagens HTTP não legíveis, como erros de formatação ou
    // de associação de propriedades inexistentes em requisições.
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException invalidformatexception) {
            return handleInvalidFormat(invalidformatexception, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException propertyBindingException) {
            return handleBindingError(propertyBindingException, headers, status, request);
        }

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(status,
                HttpErrorMessage.BAD_REQUEST, HttpErrorMessage.MALFORMED_REQUEST_BODY).build();

        return super.handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    // Método relacionados a exceções de tipo de parâmetro na URL
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatusCode status,
                                                        WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return handleArgumentTypeMismatch(methodArgumentTypeMismatchException, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    // Método relacionados a exceções de recurso não encontrado.
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatusCode status,
                                                                    WebRequest request) {
        String detail = HttpErrorMessageFormatter
            .formatString(HttpErrorMessage.ENDPOINT_NOT_FOUND, ex.getResourcePath());

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(status,
                HttpErrorMessage.ENDPOINT_NOT_FOUND_TITLE, detail).build();

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    // Método relacionados a exceções de de método HTTP não suportado.
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatusCode status,
                                                                         WebRequest request) {
        String httpMethod = ex.getMethod();
        String detail = HttpErrorMessageFormatter.formatString(HttpErrorMessage.UNSUPPORTED_HTTP_METHOD, httpMethod);

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(status,
            HttpErrorMessage.METHOD_NOT_SUPPORTED, detail).build();

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    // Métodos relacionados a exceções específicas da aplicação
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = ex.getMessage();

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(
            status, HttpErrorMessage.BAD_REQUEST, detail
        ).build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = ex.getMessage();

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(
            status, HttpErrorMessage.BAD_REQUEST, detail
        )
        .build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(
            status, HttpErrorMessage.RESOURCE_NOT_FOUND_GENERIC, detail
        )
        .build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String detail = ex.getMessage();

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(
            status, HttpErrorMessage.OPERATION_NOT_ALLOWED, detail
        )
        .build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String detail = ex.getMessage();

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(
            status, HttpErrorMessage.UNAUTHORIZED_ACCESS, detail
        )
        .build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(HttpClientException.class)
    public ResponseEntity<Object> handleHttpClientException(HttpClientException ex, WebRequest request) {
        HttpStatus status = ex.getStatus();
        HttpErrorResponse exceptionRestResponse = buildErrorResponse(
                status, "Client Error", ex.getMessage()
        ).build();

        return handleExceptionInternal(ex, exceptionRestResponse, new HttpHeaders(), status, request);
    }


    // Método padrão para lidar com exceções internas
    private ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                           HttpErrorResponse body,
                                                           HttpHeaders headers,
                                                           HttpStatus status,
                                                           WebRequest request) {
        if (body == null) {
            body = buildErrorResponse(status, status.getReasonPhrase(),
                    HttpErrorMessage.DETAIL_UNAVAILABLE).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private HttpErrorResponse.HttpErrorResponseBuilder buildErrorResponse(HttpStatusCode status,
                                                                          Object title,
                                                                          Object detail) {
        String messageTitle = HttpErrorMessageFormatter.extractExceptionMessage(title);
        String detailedMessage = HttpErrorMessageFormatter.extractExceptionMessage(detail);

        return HttpErrorResponse.builder()
                .status(status.value())
                .title(messageTitle)
                .detail(detailedMessage);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
                                                       HttpHeaders headers,
                                                       HttpStatusCode status,
                                                       WebRequest request) {
        String path = formatErrorPath(ex.getPath());

        String detail = HttpErrorMessageFormatter.formatString(HttpErrorMessage.INVALID_FIELD_FORMAT, path, ex.getValue(),
            ex.getTargetType().getSimpleName());

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(status,
            HttpErrorMessage.BAD_REQUEST, detail).build();

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleBindingError(PropertyBindingException ex,
                                                      HttpHeaders headers,
                                                      HttpStatusCode status,
                                                      WebRequest request) {
        String path = formatErrorPath(ex.getPath());

        String detail = HttpErrorMessageFormatter.formatString(HttpErrorMessage.PROPERTY_NOT_FOUND, path);

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(status,
            HttpErrorMessage.BAD_REQUEST, detail).build();

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

    private String formatErrorPath(List<JsonMappingException.Reference> references) {
        return references.stream()
            .map(JsonMappingException.Reference::getFieldName)
            .collect(Collectors.joining("."));
    }

    private ResponseEntity<Object> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                              HttpHeaders headers,
                                                              HttpStatusCode status,
                                                              WebRequest request) {
        Class<?> requiredType = ex.getRequiredType();
        String requiredTypeName = requiredType != null ? requiredType.getSimpleName() : "desconhecido";

        String detail = HttpErrorMessageFormatter.formatString(HttpErrorMessage.INVALID_URL_PARAMETER_VALUE, ex.getName(),
            ex.getValue(), requiredTypeName);

        HttpErrorResponse exceptionRestResponse = buildErrorResponse(
            status, HttpErrorMessage.INVALID_URL_PARAMETER_TYPE, detail).build();

        return handleExceptionInternal(ex, exceptionRestResponse, headers, status, request);
    }

}
