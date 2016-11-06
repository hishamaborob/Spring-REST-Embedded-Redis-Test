package io.hisham.transactionservice.transaction.repository;

import io.hisham.transactionservice.transaction.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Transactions in redis.
 *
 * @Author Hisham
 */
@Repository
public class TransactionRepoRedis implements TransactionRepo {

    @Autowired
    private RedisTemplate<String, Transaction> redisTemplate;

    @Autowired
    private RedisTemplate<String, Long> redisTemplateStringSet;

    @Autowired
    private RedisTemplate<Long, Long> redisTemplateSetLongSet;

    public static final String TRANSACTION_KEY = "transactionKey";

    /**
     * @param transactionId
     * @return
     */
    @Override
    public Transaction getTransactionByTransactionId(Long transactionId) {

        return (Transaction) this.redisTemplate.opsForHash().get(TRANSACTION_KEY, transactionId);
    }

    /**
     * @param transaction
     */
    @Override
    public void saveTransactionByTransactionId(Long transactionId, Transaction transaction) {

        redisTemplate.opsForHash().put(TRANSACTION_KEY, transactionId, transaction);
    }

    /**
     * @param type
     * @param transactionId
     */
    @Override
    public void addTransactionIdToTypeSet(String type, Long transactionId) {

        redisTemplateStringSet.opsForSet().add(type, transactionId);
    }

    @Override
    public void addTransactionIdToParentIdSet(Long ParentTransactionId, Long childTransactionId) {

        redisTemplateSetLongSet.opsForSet().add(ParentTransactionId, childTransactionId);
    }

    @Override
    public Set<Long> getChildsByParentIdSet(Long ParentTransactionId) {

        return redisTemplateSetLongSet.opsForSet().members(ParentTransactionId);
    }

    /**
     * @param type
     * @return
     */
    @Override
    public Set<Long> getKeysByType(String type) {

        return redisTemplateStringSet.opsForSet().members(type);
    }

}
