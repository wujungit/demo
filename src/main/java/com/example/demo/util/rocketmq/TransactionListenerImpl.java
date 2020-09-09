package com.example.demo.util.rocketmq;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;

public class TransactionListenerImpl implements TransactionListener {

    // 存储对应事务的状态信息 key：事务ID，value：当前事务执行的状态
    private final ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 执行本地事务
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        // 事务ID
        String transactionId = message.getTransactionId();
        //0-执行中，1-本地事务执行成功，2-本地事务执行失败
        localTrans.put(transactionId, 0);
        // 业务执行，处理本地事务，Service
        System.out.println("do service...");
        try {
            Thread.sleep(10000);
            localTrans.put(transactionId, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            localTrans.put(transactionId, 2);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * 消息回查（一分钟回查一次）
     *
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        // 获取对应事务ID
        String transactionId = messageExt.getTransactionId();
        // 获取对应事务ID的执行状态
        Integer status = localTrans.get(transactionId);
        switch (status) {
            case 0:
                return LocalTransactionState.UNKNOW;
            case 1:
                return LocalTransactionState.COMMIT_MESSAGE;
            case 2:
                return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
    }
}
