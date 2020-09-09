package com.example.demo.util.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class OrderProducer {
    public static void main(String[] args) throws Exception {
        // 创建DefaultMQProducer
        DefaultMQProducer producer = new DefaultMQProducer("demo_producer_order_group");
        // 设置Namesrv地址
        producer.setNamesrvAddr("ip:port");
        // 开启DefaultMQProducer
        producer.start();
        // 创建消息Message
        for (int i = 0; i < 5; i++) {
            // String topic, String tags, String keys, byte[] body
            Message message = new Message(
                    "demo_order_topic",
                    "tag_demo",
                    "1",
                    ("hello word: " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // 发送消息（参数1：发送的消息，参数2：选择指定的消息队列，参数3：指定对应的队列下标）
            SendResult result = producer.send(
                    message,
                    new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                            // 获取队列的下标
                            Integer index = (Integer) o;
                            // 获取对应下标的队列
                            return list.get(index);
                        }
                    },
                    0
            );
            System.out.println(JSON.toJSONString(result));
        }
        // 关闭DefaultMQProducer
        producer.shutdown();
    }
}
