package io.hisham.transactionservice.transaction.service;

import io.hisham.transactionservice.transaction.entity.Transaction;
import io.hisham.transactionservice.transaction.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

/**
 * Transactions Management Service.
 *
 * @Author Hisham
 */
@Service
public class TransactionsManagementService implements TransactionsManagement {

    @Autowired
    private TransactionRepo transactionRepoRedis;

    @Override
    public boolean saveTransaction(Long transactionId, Transaction transaction) {

        if (transactionId != null && transaction != null && transaction.getType() != null) {

            transactionRepoRedis.saveTransactionByTransactionId(transactionId, transaction);
            transactionRepoRedis.addTransactionIdToTypeSet(transaction.getType(), transactionId);
            if (transaction.getParentId() != null) {
                transactionRepoRedis.addTransactionIdToParentIdSet(
                        transaction.getParentId(), transactionId);
            }
            return true;
        }
        return false;
    }

    @Override
    public Transaction getTransaction(Long transactionId) {

        if (transactionId != null) {

            return transactionRepoRedis.getTransactionByTransactionId(transactionId);
        }
        return null;
    }

    @Override
    public Set<Long> getAllTransactionsKeysByType(String type) {

        if (type != null) {
            return transactionRepoRedis.getKeysByType(type);
        }
        return null;
    }

    @Override
    public Double getChildsAmountSumByTransactionId(Long transactionId) {

        return incrementTransaction(transactionId);
    }

    private Double incrementTransaction(Long transactionId) {

        Transaction transaction =
                transactionRepoRedis.getTransactionByTransactionId(transactionId);

        Double sum = new Double(0);
        if (transaction.getAmount() > 0) {
            sum += transaction.getAmount();
        }
        Set<Long> childs = transactionRepoRedis.getChildsByParentIdSet(transactionId);

        if (!childs.isEmpty()) {
            sum += childs.stream().mapToDouble(i -> incrementTransaction(i)).sum();
        }
        return sum;
    }

}
