package hello.proxy.app.config.v2_dynamicproxy;

import hello.proxy.app.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {

    /**
     *
     * @param logTrace
     * @return
     */
    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        /**
         * OrderControllerImpl은 내부에 orderService를 참조하고 있다.
         */
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
        OrderControllerV1 proxy = (OrderControllerV1)
                Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceBasicHandler(orderControllerV1, logTrace));
        return proxy;
    }


    /**
     *
     * @param logTrace
     * @return
     */
    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(
                        OrderServiceV1.class.getClassLoader(),
                        new Class[]{OrderServiceV1.class},
                        new LogTraceBasicHandler(orderServiceV1, logTrace));
        return proxy;
    }

    /**
     * Spring 컨테이너가 관리하는 Bean 중에서 LogTrace 타입의 Bean을 찾아서 주입
     * @param logTrace
     * @return
     */
    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();

        //Dynamic Proxy
        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceBasicHandler(orderRepository, logTrace));
        return proxy;
    }
}
