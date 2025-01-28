package hello.proxy.app.config.v1_proxy;

import hello.proxy.app.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import hello.proxy.app.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import hello.proxy.app.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 인터페이스 기반 프록시 vs 클래스 기반 프록시
 * 인터페이스 도입은 구현을 변경할 일이 있을 떄 사용한다.
 * 구현을 변경하지 않는 클래스에서의 인터페이스 도입은 실용적이지 않다.
 */

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 orderServiceImpl = new OrderServiceV2(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(orderServiceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryImpl = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(orderRepositoryImpl, logTrace);
    }

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 orderControllerImpl = new OrderControllerV2(orderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(orderControllerImpl, logTrace);
    }
}
