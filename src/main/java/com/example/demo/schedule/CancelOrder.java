package com.example.demo.schedule;

import com.example.demo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Slf4j
public class CancelOrder {

    private final Logger logger = LoggerFactory.getLogger(CancelOrder.class);

    @Autowired
    private RedisUtil redisUtil;

    private static final String LOCK_KEY = "";

    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelOrder() {
        logger.info("定时任务启动..................当前时间:{}", new Date());

        //因为setnxAndExpire方法设置值与设置时间是原子操作，所以就可以达到分布式锁的目的
        //大家如果去看到另外的版本 setnx与expire两个方法 是分开的那么就不是原子操作，这两个方法，执行期间可能会出现服务器宕机什么的造成死锁

        boolean flag = redisUtil.setnxAndExpire(LOCK_KEY, String.valueOf(System.currentTimeMillis() + 50000), -1);
        if (flag) {
            try {
                logger.info("获得分布式锁...");

                // 进行对应的操作

            } finally {
                //不管时间到没到，业务做完了就可以释放锁了
                // 释放分布式锁
                redisUtil.del(LOCK_KEY);
            }
        }
    }
}
