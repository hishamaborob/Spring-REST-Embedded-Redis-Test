package io.hisham.transactionservice.handler.helper;

import io.hisham.transactionservice.handler.exception.rest.RestExceptionAbstract;
import org.springframework.http.HttpStatus;

/**
 * Generator for ErrorInfo entity.
 *
 * @Author Hisham
 */
public class ErrorInfoGenerator {

    /**
     * To be used when exception is an instance of RestExceptionAbstract.
     *
     * @param exception
     * @return ErrorInfo
     */
    public static ResponseInfo generateErrorInfo(Throwable exception) {
        if (exception instanceof RestExceptionAbstract) {
            return generateErrorInfo(exception, ((RestExceptionAbstract) exception).getCode());
        } else {
            return generateErrorInfo(exception, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * To be used when exception is an instance of Throwable.
     *
     * @param exception
     * @return ErrorInfo
     */
    public static ResponseInfo generateErrorInfo(Throwable exception, int errorCode) {
        String developerMessage;
        if (exception.getMessage() == null) {
            developerMessage = ErrorCode.ERROR_CODES.get(errorCode).get("developerMessage");
        } else {
            developerMessage = exception.getMessage();
        }

        ResponseInfo errorInfo = new ResponseInfo();
        errorInfo.setStatus(ErrorCode.ERROR_CODES.get(errorCode).get("status"));
        errorInfo.setCode(ErrorCode.ERROR_CODES.get(errorCode).get("code"));
        errorInfo.setMessage(ErrorCode.ERROR_CODES.get(errorCode).get("message"));
        errorInfo.setDeveloperMessage(developerMessage);
        errorInfo.setMoreInfo(ErrorCode.ERROR_CODES.get(errorCode).get("moreInfo"));
        return errorInfo;
    }
}
