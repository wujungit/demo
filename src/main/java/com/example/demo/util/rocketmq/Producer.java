package com.example.demo.util.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class Producer {
    public static void main(String[] args) throws Exception {
        // 创建DefaultMQProducer
        DefaultMQProducer producer = new DefaultMQProducer("demo_producer_group");
        // 设置Namesrv地址
        producer.setNamesrvAddr("ip:port");
        // 开启DefaultMQProducer
        producer.start();
        // 创建消息Message
        // String topic, String tags, String keys, byte[] body
        Message message = new Message(
                "demo_topic",
                "tag_demo",
                "1",
                "hello word".getBytes(RemotingHelper.DEFAULT_CHARSET)
        );
        // 发送消息
        SendResult result = producer.send(message);
        System.out.println(JSON.toJSONString(result));
        // 关闭DefaultMQProducer
        producer.shutdown();
    }
}
