package io.hisham.transactionservice.v1.controllers;

import io.hisham.transactionservice.handler.exception.DataNotFoundException;
import io.hisham.transactionservice.handler.exception.rest.ClientErrorException;
import io.hisham.transactionservice.handler.helper.ResponseInfo;
import io.hisham.transactionservice.transaction.entity.Transaction;
import io.hisham.transactionservice.transaction.service.TransactionsManagement;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


/**
 * @author Hisham
 */
@Controller
@RequestMapping("/v1/transactionservice")
public class TransactionController {

    @Autowired
    TransactionsManagement transactionsManagementService;

    @RequestMapping(value = "/transaction/{transaction_id}", method = PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, String> saveTransaction(
            @RequestBody Transaction transaction, @PathVariable Long transaction_id)
            throws ClientErrorException {

        Map<String, String> responseInfo = new HashMap<String, String>();
        boolean created = transactionsManagementService.saveTransaction(transaction_id, transaction);
        if (created) {
            responseInfo.put("status", "ok");
            return responseInfo;
        }
        throw new ClientErrorException(HttpStatus.BAD_REQUEST.value());
    }

    @RequestMapping(value = "/transaction/{transaction_id}", method = GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Transaction getTransaction(@PathVariable Long transaction_id)
            throws DataNotFoundException {

        Transaction transaction = transactionsManagementService.getTransaction(transaction_id);

        if (transaction != null) {
            return transaction;
        }
        throw new DataNotFoundException("No transaction found!");
    }


    @RequestMapping(value = "/types/{type}", method = GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<Long> getTransactionKeysByType(@PathVariable String type)
            throws DataNotFoundException {

        Set<Long> transactionskeys = transactionsManagementService.getAllTransactionsKeysByType(type);

        if (!transactionskeys.isEmpty()) {
            return transactionskeys;
        }
        throw new DataNotFoundException("No transactions found!");
    }

    @RequestMapping(value = "/sum/{transaction_id}", method = GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> getSumByTransactionId(@PathVariable Long transaction_id)
            throws DataNotFoundException {

        Map<String, Double> responseInfo = new HashMap<String, Double>();

        Double sum = transactionsManagementService.getChildsAmountSumByTransactionId(transaction_id);

        if (sum > 0) {
            responseInfo.put("sum", sum);
            return responseInfo;
        }
        throw new DataNotFoundException("No transactions found!");
    }

}
