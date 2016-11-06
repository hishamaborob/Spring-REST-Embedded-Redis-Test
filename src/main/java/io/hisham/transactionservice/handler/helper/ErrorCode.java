package io.hisham.transactionservice.handler.helper;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Error info data and messages.
 *
 * @Author Hisham
 */
public class ErrorCode {

    /**
     * Default more_info param value.
     */
    public static final String MORE_INFO = "Please call hisham.aborob@gmail.com";

    /**
     * Default developer message param value.
     */
    public static final String DEVELOPER_MESSAGE = "No message.";

    /**
     * Hash Map for error info to be returned based on http code.
     */
    public static final Map<Integer, HashMap<String, String>> ERROR_CODES = new HashMap<Integer, HashMap<String, String>>();

    /**
     * Adding and new code default info to the map.
     */
    static {
        ERROR_CODES.put(HttpStatus.BAD_REQUEST.value(), new HashMap<String, String>() {{
            put("status", "400");
            put("code", "400");
            put("message", "Bad Request!");
            put("moreInfo", MORE_INFO);
            put("developerMessage", DEVELOPER_MESSAGE);
        }});
        ERROR_CODES.put(HttpStatus.NOT_FOUND.value(), new HashMap<String, String>() {{
            put("status", "404");
            put("code", "404");
            put("message", "There is no resource for the requested path!");
            put("moreInfo", MORE_INFO);
            put("developerMessage", DEVELOPER_MESSAGE);
        }});
        ERROR_CODES.put(HttpStatus.INTERNAL_SERVER_ERROR.value(), new HashMap<String, String>() {{
            put("status", "500");
            put("code", "500");
            put("message", "An error occurred! Please try again in few minutes.");
            put("moreInfo", MORE_INFO);
            put("developerMessage", DEVELOPER_MESSAGE);
        }});
    }
}
