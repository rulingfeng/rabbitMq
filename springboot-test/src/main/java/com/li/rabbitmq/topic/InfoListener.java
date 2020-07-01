package com.li.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-rabbitmq
 * @description:
 * @author: 栗翱
 * @create: 2020-05-06 14:49
 **/
@Configuration
public class InfoListener {

    /**
     * 监听指定 消息队列
     *
     * @param message
     */
    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "info.log", durable = "true"),
            exchange = @Exchange(name = "log.topic", durable = "true", type = "topic"),
            key = "info.*"
        )
    )
    public void aaa(String message) {
        System.out.println("info消息监听：" + message);
    }

}
