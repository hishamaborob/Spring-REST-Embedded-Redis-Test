package io.hisham.transactionservice.transaction.service;

import io.hisham.transactionservice.transaction.entity.Transaction;

import java.util.Set;

/**
 * Transactions Management Service.
 *
 * @Author Hisham
 */
public interface TransactionsManagement {

    public boolean saveTransaction(Long transactionId, Transaction transaction);

    public Transaction getTransaction(Long transactionId);

    public Set<Long> getAllTransactionsKeysByType(String type);

    public Double getChildsAmountSumByTransactionId(Long transactionId);

}
