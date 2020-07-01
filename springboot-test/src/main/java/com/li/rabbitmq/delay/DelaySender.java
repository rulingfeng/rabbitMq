package com.li.rabbitmq.delay;

import com.alibaba.fastjson.JSONObject;
import com.sxw.entity.Order;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的发出者
 */
@Component
public class DelaySender {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(){
        Order order = new Order();
        order.setId(10086);
        order.setName("delay");
        order.setMessageId("delay-test");

        rabbitTemplate.convertAndSend(QueueEnum.QUEUE_DXL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_DXL_ORDER_CANCEL.getRouteKey(),order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(1000*30));
                System.out.println("延迟队列发送数据");
                return message;
            }
        });
    }


}
