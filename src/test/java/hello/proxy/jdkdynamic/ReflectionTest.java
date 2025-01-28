package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection_test_0() {
        Hello target = new Hello();

        /** 공통 로직1 시작 **/
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        /** 공통 로직1 종료 **/

        /** 공통 로직2 시작 **/
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
        /** 공통 로직2 종료 **/
    }

    @Test
    void reflection_test_2() throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        /**
         * targetA
         */
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        /**
         * targetB
         */
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallA.invoke(target);
        log.info("result2={}", result2);

    }

    @Test
    void reflection_test_3() throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        /**
         * targetA
         */
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        /**
         * targetB
         */
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    /**
     * Reflection Method
     * @param method
     * @param target
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void dynamicCall(Method method, Object target) throws
            InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
        log.info("end");
    }


    @Slf4j
    static class Hello {

        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }

}
