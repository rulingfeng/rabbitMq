package com.li.rabbitmq.ack;

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
public class AckListener {

    /**
     * 监听指定 消息队列
     *
     * @param order
     */
    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ack.test", durable = "true"),
            exchange = @Exchange(name = "ack.direct", durable = "true", type = "direct"),
            key = "ack.direct.test"
    )
    )
    @RabbitHandler//如果有消息过来，在消费的时候调用这个方法
    public void aaa(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        //Delivery Tag 用来标识信道中投递的消息。RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
        //RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //消费者操作
        try {
            System.out.println("ack消息监听：" + order);

            //flag 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
            //如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
            Boolean flag = false;
//            channel.basicNack(tag, false, true);
            //ACK,确认一条消息已经被消费
            channel.basicAck(tag, flag);

        } catch (Exception e) {
            System.out.println(e.getMessage());
//            channel.basicReject(tag, true);
            channel.basicNack(tag, false, true);
        }


    }

}
