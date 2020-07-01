package com.li.rabbitmq.delay;

import com.rabbitmq.client.Channel;
import com.sxw.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * @program: springboot-rabbitmq
 * @description:
 * @author: 栗翱
 * @create: 2020-05-06 14:49
 **/
@Configuration
public class ConsumerListener {

    /**
     * 监听指定 消息队列
     *
     * @param order
     */
    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "mall.queue", durable = "true"),
            exchange = @Exchange(name = "mall.test", durable = "true", type = "direct"),
            key = "mall.test"
        )
    )
    @RabbitHandler//如果有消息过来，在消费的时候调用这个方法
    public void aaa(@Payload Order order) {
        System.out.println("延迟过期转队列消息监听：" + order);
        //处理 延时过期业务
    }

}
