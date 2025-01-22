package hello.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        if (cacheValue == null) {
            //chaceValue에 Subject.operation 반환값을 저장한다.
            cacheValue = target.operation();
        }
        return cacheValue;
    }
}
