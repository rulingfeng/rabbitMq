package com.li.controller;

import com.li.rabbitmq.ack.AckSender;
import com.li.rabbitmq.delay.DelaySender;
import com.li.rabbitmq.routing.OrderSender;
import com.li.rabbitmq.topic.ErrorSender;
import com.li.rabbitmq.topic.InfoSender;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: springboot-rabbitmq
 * @description:
 * @author: 栗翱
 * @create: 2020-05-06 15:22
 **/
@Controller
public class TestController {
    @Autowired
    private OrderSender orderSender;
    @Autowired
    private ErrorSender errorSender;
    @Autowired
    private InfoSender infoSender;
    @Autowired
    private AckSender ackSender;
    @Autowired
    private DelaySender delaySender;

    @GetMapping("/aaa")
    @ApiOperation(value = "测试", notes = "测试")
    @ResponseBody
    public void test1() throws Exception {
        orderSender.send();
    }

    @GetMapping("/topic")
    @ApiOperation(value = "测试", notes = "测试")
    @ResponseBody
    public void topic() throws Exception {
        errorSender.send();
        infoSender.send();
    }

    @GetMapping("/ack")
    @ApiOperation(value = "ack", notes = "测试")
    @ResponseBody
    public void ack() throws Exception {
        ackSender.send();
    }

    @GetMapping("/delay")
    @ApiOperation(value = "delay", notes = "测试")
    @ResponseBody
    public void delay() throws Exception {
        delaySender.send();
    }
}
