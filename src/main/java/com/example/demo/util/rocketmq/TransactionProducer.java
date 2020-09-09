package com.example.demo.util.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.*;

public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        // 创建DefaultMQProducer
        TransactionMQProducer producer = new TransactionMQProducer("demo_producer_transaction_group");
        // 设置Namesrv地址
        producer.setNamesrvAddr("ip:port");
        // 指定消息监听对象，用于执行本地事务和消息回查
        producer.setTransactionListener(new TransactionListenerImpl());
        // 线程池
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000, true)
        );
        producer.setExecutorService(executorService);
        // 开启DefaultMQProducer
        producer.start();
        // 创建消息Message：String topic, String tags, String keys, byte[] body
        Message message = new Message(
                "demo_transaction_topic",
                "tag_demo",
                "1",
                ("hello word").getBytes(RemotingHelper.DEFAULT_CHARSET)
        );
        // 发送事务消息
        TransactionSendResult result = producer.sendMessageInTransaction(message, "hello-transaction");
        System.out.println(JSON.toJSONString(result));
        // 关闭DefaultMQProducer
        producer.shutdown();
    }
}
