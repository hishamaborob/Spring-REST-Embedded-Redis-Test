package io.hisham.transactionservice.handler.exception;

/**
 * If no data was found for required key or value flag.
 *
 * @Author Hisham
 */
public class DataNotFoundException extends Exception implements APIException {

    public DataNotFoundException(String msg) {
        super(msg);
    }
}
