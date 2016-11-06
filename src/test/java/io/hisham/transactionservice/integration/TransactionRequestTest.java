package io.hisham.transactionservice.integration;

import io.hisham.transactionservice.transaction.service.TransactionsManagement;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hisham.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TransactionRequestTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private MockMvc mockMvc;

    private static TransactionRequestTest lastTestInstance;

    @AfterClass
    public static synchronized void cleanUp() throws Exception {
        lastTestInstance.redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        synchronized (TransactionRequestTest.class) {
            if (lastTestInstance == null) {
                lastTestInstance = this;
            }
        }
    }

    @Test
    public void test_a_saveTransaction() throws Exception {

        mockMvc.perform(
                put("/v1/transactionservice/transaction/{id}", 10)
                        .contentType(MediaType.APPLICATION_JSON).content(
                        "{\"amount\":5000,\"type\":\"cars\"}"
                )
        )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"ok\"}"));

        mockMvc.perform(
                put("/v1/transactionservice/transaction/{id}", 11)
                        .contentType(MediaType.APPLICATION_JSON).content(
                        "{\"amount\":6000,\"type\":\"cars\",\"parent_id\":10}"
                )
        )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"ok\"}"));
    }

    @Test
    public void test_b_getTransaction() throws Exception {

        mockMvc.perform(
                get("/v1/transactionservice/transaction/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"amount\":5000.0,\"type\":\"cars\"}"));
    }

    @Test
    public void test_c_getKeysByType() throws Exception {

        mockMvc.perform(
                get("/v1/transactionservice/types/{type}", "cars"))
                .andExpect(status().isOk())
                .andExpect(content().json("[10,11]"));
    }

    @Test
    public void test_d_getSumByParentId() throws Exception {

        mockMvc.perform(
                get("/v1/transactionservice/sum/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"sum\":11000.0}"));

        mockMvc.perform(
                get("/v1/transactionservice/sum/{id}", 11))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"sum\":6000.0}"));
    }

}
