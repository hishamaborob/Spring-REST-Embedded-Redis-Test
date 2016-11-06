package io.hisham.transactionservice.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import io.hisham.transactionservice.handler.exception.rest.ClientErrorException;

/**
 * Default controller that exists to return a
 * proper REST response for unmapped requests.
 *
 * @Author Hisham
 */
@Controller
public class DefaultController {

    /**
     * @throws ClientErrorException with 404 status.
     */
    @RequestMapping("/errorNotFound")
    public void unmappedRequest() throws ClientErrorException {
        throw new ClientErrorException(HttpStatus.NOT_FOUND.value(), "This request could not be handled!");
    }

    /**
     * @throws Exception with 500 status.
     */
    @RequestMapping("/errorServer")
    public void errorServer() throws Exception {
        throw new Exception("This request could not be handled!");
    }
}
