package hello.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    // 프록시는 호출 할 대상 클래스가 필요하다.
    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    /**
     *
     * invoke
     * @param obj
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("TimeProxy 종료 resultTime={}", timeCalculate(System.currentTimeMillis(), System.currentTimeMillis()));
        return proxy.invoke(target, args);
    }

    private long timeCalculate(long startTime, long endTime) {
        return endTime - startTime;
    }
}
