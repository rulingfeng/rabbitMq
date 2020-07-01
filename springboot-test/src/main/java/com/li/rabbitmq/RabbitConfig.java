package com.li.rabbitmq;

import com.li.rabbitmq.delay.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-rabbitmq
 * @description:
 * @author: 栗翱
 * @create: 2020-05-06 14:47
 **/
@Configuration
public class RabbitConfig {


    @Bean
//    @Qualifier("orderQueue")
    public Queue orderQueue() {
        return new Queue("vinsuan.test"); //队列名称
    }


    @Bean
//    @Qualifier("orderDirect")
    DirectExchange orderDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange("vinsuan.exchange.test")
                .durable(true)
                .build();
    }

    /**
     * 将订单队列绑定到交换机
     */
    @Bean
//    @Qualifier("orderBinding")
    Binding orderBinding(DirectExchange orderDirect, Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with("vinsuan.routingKey.test");
    }

    /**
     *  死信队列
     * @return
     */
    @Bean
//    @Qualifier("delayQueue")
    public Queue delayQueue() {
        return  QueueBuilder
                .durable(QueueEnum.QUEUE_DXL_ORDER_CANCEL.getName())
                //过期后转发的交换机  x-dead-letter-exchange 固定值
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                //过期后转发的路由键  x-dead-letter-routing-key 固定值
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())
                .build();
    }

    /**
     * 死信队列的交换机
     * @return
     */
    @Bean
//    @Qualifier("delayQueue")
    DirectExchange delayExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_DXL_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    @Bean
//    @Qualifier("delayBinding")
    Binding delayBinding(DirectExchange delayExchange, Queue delayQueue) {
        return BindingBuilder
                .bind(delayQueue)
                .to(delayExchange)
                .with(QueueEnum.QUEUE_DXL_ORDER_CANCEL.getRouteKey());

    }

//    @Bean
//    public Queue logQueue() {
//        return new Queue("all.log");
//    }
//
//    @Bean
//    public Queue errorLogQueue() {
//        return new Queue("error.log");
//    }
//
//    @Bean
//    DirectExchange logDirect() {
//        return (DirectExchange) ExchangeBuilder
//                .directExchange("log.topic")
//                .durable(true)
//                .build();
//    }
//
//    @Bean
//    Binding logBinding(DirectExchange logDirect, Queue errorLogQueue){
//        return BindingBuilder
//                .bind(errorLogQueue)
//                .to(logDirect)
//                .with("error.*");
//    }
//
//    @Bean
//    Binding errorBinding(DirectExchange logDirect, Queue logQueue){
//        return BindingBuilder
//                .bind(logQueue)
//                .to(logDirect)
//                .with("*.log");
//    }
}