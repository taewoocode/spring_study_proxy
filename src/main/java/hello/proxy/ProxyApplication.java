package hello.proxy;

import hello.proxy.app.config.AppV2Config;
import hello.proxy.app.config.InterfaceProxyConfig;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


//@Import(AppV1Config.class)
//@Import({AppV1Config.class, AppV2Config.class})
@Import(InterfaceProxyConfig.class)
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //주의
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
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
