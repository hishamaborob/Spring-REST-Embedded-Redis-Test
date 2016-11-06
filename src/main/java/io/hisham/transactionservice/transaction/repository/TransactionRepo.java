package io.hisham.transactionservice.transaction.repository;

import io.hisham.transactionservice.transaction.entity.Transaction;

import java.util.Set;

/**
 * Transaction.
 *
 * @Author Hisham
 */
public interface TransactionRepo {

    public Transaction getTransactionByTransactionId(Long transactionId);

    public void saveTransactionByTransactionId(Long transactionId, Transaction transaction);

    public void addTransactionIdToTypeSet(String type, Long transactionId);

    public void addTransactionIdToParentIdSet(Long ParentTransactionId, Long childTransactionId);

    public Set<Long> getChildsByParentIdSet(Long ParentTransactionId);

    public Set<Long> getKeysByType(String type);

}
