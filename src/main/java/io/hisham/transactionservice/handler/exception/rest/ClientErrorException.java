package io.hisham.transactionservice.handler.exception.rest;

import io.hisham.transactionservice.handler.helper.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Handling client errors.
 *
 * @Author Hisham
 */
public class ClientErrorException extends RestExceptionAbstract {

    /**
     * Construct and validate client error.
     *
     * @param code
     */
    public ClientErrorException(int code) {
        this(code, ErrorCode.DEVELOPER_MESSAGE);
    }

    /**
     * Construct and validate client error.
     *
     * @param code
     * @param developerMessage
     */
    public ClientErrorException(int code, String developerMessage) {
        super(developerMessage);
        try {
            if (!HttpStatus.valueOf(code).is4xxClientError()) {
                code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
        } catch (IllegalArgumentException e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        setCode(code);
    }
}
