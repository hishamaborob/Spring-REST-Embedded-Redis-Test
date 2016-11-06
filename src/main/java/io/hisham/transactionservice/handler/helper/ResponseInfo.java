package io.hisham.transactionservice.handler.helper;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Error info HTTP response entity.
 *
 * @Author Hisham
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo {

    /**
     * HTTP status code.
     */
    private String status;

    /**
     * Error custom unique code.
     */
    private String code;

    /**
     * Message related to the error.
     */
    private String message;

    /**
     * Custom message by developer.
     */
    private String developerMessage;

    /**
     * More info to support.
     */
    private String moreInfo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", developerMessage='" + developerMessage + '\'' +
                ", moreInfo='" + moreInfo + '\'' +
                '}';
    }
}
