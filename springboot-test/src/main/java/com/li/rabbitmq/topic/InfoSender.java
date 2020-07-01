package com.li.rabbitmq.topic;

import com.alibaba.fastjson.JSONObject;
import com.sxw.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfoSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 还要在 rabbitmq 控制台配置exchange和queue，并绑定
     * 加绑定在控制台的exchange和queues哪一块都可以
     */
    public void send(){
        Order order = new Order();
        order.setId(1);
        order.setName("info");
        order.setMessageId("info.log");
        String message = JSONObject.toJSONString(order);
        rabbitTemplate.convertAndSend("log.topic","info.log", message);
        System.out.println("发送数据");
    }


}
