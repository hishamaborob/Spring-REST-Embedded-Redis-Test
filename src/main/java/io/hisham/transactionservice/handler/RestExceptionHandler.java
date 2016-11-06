package io.hisham.transactionservice.handler;

import io.hisham.transactionservice.handler.exception.DataNotFoundException;
import io.hisham.transactionservice.handler.exception.rest.RestExceptionAbstract;
import io.hisham.transactionservice.handler.helper.ResponseInfo;
import io.hisham.transactionservice.handler.helper.ErrorInfoGenerator;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Catching different kinds of exceptions.
 *
 * @Author Hisham
 * @TODO !! Old class !! to refactor to lambadas
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = Logger.getLogger(RestExceptionHandler.class);

    /**
     * Catching all client errors exceptions.
     *
     * @param exception
     * @return Response entity
     */
    @ExceptionHandler({RestExceptionAbstract.class})
    @ResponseBody
    public ResponseEntity<ResponseInfo> handleGeneralException(RestExceptionAbstract exception) {
        ResponseInfo errorInfo = ErrorInfoGenerator.generateErrorInfo(exception);
        ResponseEntity<ResponseInfo> responseEntity = new ResponseEntity<ResponseInfo>(errorInfo,
                HttpStatus.valueOf(exception.getCode()));
        logger.info("CLIENT_ERROR_EXCEPTION_HANDLER: " + responseEntity.toString(), exception);
        return responseEntity;
    }

    /**
     * Catching all MethodArgumentTypeMismatchException that can happened by passing wrong params.
     *
     * @param exception
     * @return Response entity
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<ResponseInfo> handleMiscFailures(Throwable exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseInfo errorInfo = ErrorInfoGenerator.generateErrorInfo(exception, httpStatus.value());
        ResponseEntity<ResponseInfo> responseEntity = new ResponseEntity<ResponseInfo>(errorInfo, httpStatus);
        logger.info("VALIDATION_EXCEPTION_HANDLER: " + responseEntity.toString(), exception);
        return responseEntity;
    }

    /**
     * Catching all DataNotFoundException.
     *
     * @param exception
     * @return Response entity
     */
    @ExceptionHandler({DataNotFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResponseEntity<ResponseInfo> handleDataNotFoundForResources(Throwable exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ResponseInfo errorInfo = ErrorInfoGenerator.generateErrorInfo(exception, httpStatus.value());
        ResponseEntity<ResponseInfo> responseEntity = new ResponseEntity<ResponseInfo>(errorInfo, httpStatus);
        logger.info("DATA_EXCEPTION_HANDLER: " + responseEntity.toString(), exception);
        return responseEntity;
    }

    /**
     * Catching all errors exceptions.
     *
     * @param exception
     * @return Response entity
     */
    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public ResponseEntity<ResponseInfo> handleAnyException(Throwable exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseInfo errorInfo = ErrorInfoGenerator.generateErrorInfo(exception, httpStatus.value());
        ResponseEntity<ResponseInfo> responseEntity = new ResponseEntity<ResponseInfo>(errorInfo, httpStatus);
        logger.error("INTERNAL_SERVER_ERROR_EXCEPTION_HANDLER: " + responseEntity.toString(), exception);
        return responseEntity;
    }
}
