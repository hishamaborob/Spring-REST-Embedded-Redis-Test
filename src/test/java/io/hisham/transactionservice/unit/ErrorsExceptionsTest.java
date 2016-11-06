package io.hisham.transactionservice.unit;

import io.hisham.transactionservice.handler.exception.rest.ClientErrorException;
import io.hisham.transactionservice.handler.RestExceptionHandler;
import io.hisham.transactionservice.handler.helper.ErrorCode;
import io.hisham.transactionservice.handler.helper.ResponseInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by hisham.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context.xml"})
public class ErrorsExceptionsTest {

    @Test
    public void ErrorsExceptionsTest() throws IOException, ServletException {

        String testMessage = "This is test message";
        ClientErrorException validException1 = new ClientErrorException(HttpStatus.NOT_FOUND.value(), testMessage);
        ClientErrorException validException2 = new ClientErrorException(HttpStatus.FORBIDDEN.value());
        ClientErrorException invalidHandledException1 = new ClientErrorException(HttpStatus.ACCEPTED.value(), testMessage);
        ClientErrorException invalidHandledException2 = new ClientErrorException(HttpStatus.OK.value());

        /////
        Assert.assertEquals(testMessage, validException1.getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), validException1.getCode());

        Assert.assertEquals(ErrorCode.DEVELOPER_MESSAGE, validException2.getMessage());
        Assert.assertEquals(HttpStatus.FORBIDDEN.value(), validException2.getCode());

        Assert.assertEquals(testMessage, invalidHandledException1.getMessage());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), invalidHandledException1.getCode());

        Assert.assertEquals(ErrorCode.DEVELOPER_MESSAGE, invalidHandledException2.getMessage());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), invalidHandledException2.getCode());

        /////
        RestExceptionHandler restExceptionHandler = new RestExceptionHandler();
        /////

        ResponseEntity<ResponseInfo> entityNotFound =
                restExceptionHandler.handleGeneralException(validException1);
        Assert.assertEquals("" + HttpStatus.NOT_FOUND.value(), entityNotFound.getBody().getStatus());
        Assert.assertEquals(ErrorCode.ERROR_CODES.get(HttpStatus.NOT_FOUND.value()).get("message"),
                entityNotFound.getBody().getMessage());

        ResponseEntity<ResponseInfo> entityInternalError =
                restExceptionHandler.handleGeneralException(invalidHandledException2);
        Assert.assertEquals("" + HttpStatus.INTERNAL_SERVER_ERROR.value(), entityInternalError.getBody().getStatus());
        Assert.assertEquals(ErrorCode.ERROR_CODES.get(HttpStatus.INTERNAL_SERVER_ERROR.value()).get("message"),
                entityInternalError.getBody().getMessage());

        /////

    }

}
