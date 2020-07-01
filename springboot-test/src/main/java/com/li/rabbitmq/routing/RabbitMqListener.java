package com.li.rabbitmq.routing;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-rabbitmq
 * @description:
 * @author: 栗翱
 * @create: 2020-05-06 14:49
 **/
@Configuration
public class RabbitMqListener {

    /**
     * 监听指定 消息队列
     * @param message
     */
    @RabbitListener(queues = "vinsuan.test")
    public void aaa(String message ){
        System.out.println("消息监听："+ message);
    }

}
