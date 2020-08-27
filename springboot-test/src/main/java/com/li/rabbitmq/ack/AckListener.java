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
        System.out.println("tag:"+ tag);
        //消费者操作
        try {
            System.out.println("ack消息监听：" + order);

            //flag 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
            //如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
            Boolean flag = false;
            //int i = 1/0;
//            channel.basicNack(tag, false, true);
            //ACK,确认一条消息已经被消费
            channel.basicAck(tag, flag);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            //BasicReject一次只能拒绝接收一个消息，而BasicNack方法可以支持一次0个或多个消息的拒收，并且也可以设置是否requeue。
            //在第一个参数DeliveryTag中如果输入3，则消息DeliveryTag小于等于3的，这个Channel的，都会被拒收
            //最后一个参数 requeue 设置为true 会把消费失败的消息从新添加到队列的尾端，设置为false不会重新回到队列
            /**
             * BasicReject方法第一个参数是消息的DeliveryTag，对于每个Channel来说，每个消息都会有一个DeliveryTag，
             * 一般用接收消息的顺序来表示：1,2,3,4 等等。第二个参数是是否放回queue中，requeue。
             */
            //channel.basicReject(tag, true);

            channel.basicNack(tag, false, true);
        }


    }

}
