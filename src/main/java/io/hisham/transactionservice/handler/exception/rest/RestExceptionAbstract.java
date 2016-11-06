package io.hisham.transactionservice.handler.exception.rest;

import io.hisham.transactionservice.handler.exception.APIException;
import org.springframework.http.HttpStatus;

/**
 * Rest exception abstraction.
 *
 * @Author Hisham
 */
abstract public class RestExceptionAbstract extends RuntimeException implements APIException {

    /**
     * The HTTP code to be returned.
     */
    protected int code;

    /**
     * Construct the HTTP code and developer message to be returned.
     *
     * @param code
     * @param developerMessage
     */
    public RestExceptionAbstract(int code, String developerMessage) {
        super(developerMessage);
        this.setAndValidateCode(code);
    }

    /**
     * Construct the developer message and default error code status to be returned.
     *
     * @param developerMessage
     */
    public RestExceptionAbstract(String developerMessage) {
        super(developerMessage);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    /**
     * Construct the HTTP code to be returned.
     *
     * @param code
     */
    public RestExceptionAbstract(int code) {
        super();
        this.setAndValidateCode(code);
    }

    /**
     * Set and validate HTTP status code.
     *
     * @param code
     */
    protected void setAndValidateCode(int code) {
        try {
            HttpStatus.valueOf(code);
        } catch (IllegalArgumentException e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        this.code = code;
    }

    /**
     * Set HTTP status code without validation.
     *
     * @param code
     */
    protected void setCode(int code) {
        this.code = code;
    }

    /**
     * @return HTTP status code.
     */
    public int getCode() {
        return this.code;
    }
}
