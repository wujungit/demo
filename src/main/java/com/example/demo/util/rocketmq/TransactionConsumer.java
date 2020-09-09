package com.example.demo.util.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TransactionConsumer {
    public static void main(String[] args) throws Exception {
        // 创建DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("demo_consumer_transaction_group");
        // 设置Namesrv地址
        consumer.setNamesrvAddr("ip:port");
        // 设置消息拉取上限
        consumer.setConsumeMessageBatchMaxSize(2);
        // 设置Subscribe，这里是要读取的主题信息
        consumer.subscribe("demo_transaction_topic", "tag_demo");
        // 创建消息监听MessageListener
        consumer.setMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt messageExt : list) {
                    try {
                        // 获取消息信息
                        byte[] body = messageExt.getBody();
                        String result = new String(body, RemotingHelper.DEFAULT_CHARSET);
                        System.out.println(result);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        // 消息重试
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                }
                // 返回消息读取状态
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        // 开启Consumer
        consumer.start();
    }
}
