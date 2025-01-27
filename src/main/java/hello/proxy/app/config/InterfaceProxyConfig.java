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
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
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

    /**
     * 현재 컴파일 에러가 나는 이유는
     * logTrace 객체가 어떻게 만들어지는지 Spring이 모른다.
     * 이 객체는 LogTrace라는 인터페이스 타입이지만, 그 인터페이스를 구현한 구체적인 클래스가 무엇인지 Spring이 알지 못해서
     * 등록해줘야 한다.
     * @return
     */
    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
