package hello.proxy.app.config;

import hello.proxy.app.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.app.config.v1_proxy.interface_proxy.OrderRepositoryProxy;
import hello.proxy.app.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    /**
     * Controller는 ControllerProxy 생성
     *
     * @param logTrace
     * @return
     */
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        //의존관계 주입으로 들어간다
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    /**
     * OrderRepositoryProxy를 SpringBean으로 등록
     * @param logTrace
     * @return
     */
    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryProxy(repositoryImpl, logTrace);
    }
}
