package com.li.rabbitmq.delay;

/**
 * @program: springboot-rabbitmq
 * @description:
 * @author: 栗翱
 * @create: 2020-05-08 13:06
 **/
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("mall.test", "mall.queue", "mall.test"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_DXL_ORDER_CANCEL("mall.dxl.test", "mall.dxl.queue", "mall.dxl.test");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }
}
