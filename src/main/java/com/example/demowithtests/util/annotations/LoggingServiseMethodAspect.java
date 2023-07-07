package com.example.demowithtests.util.annotations;



import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Controller;

@Slf4j
@Aspect
@Controller
public class LoggingServiseMethodAspect {
    @Pointcut("execution(public * com.example.demowithtests.service.*Service.getById(*))")
           public  void getByIdServiceMethod(){}

    @Before(value = "getByIdServiceMethod()" +
                    " && args(id)" +
                    " && target(service)", argNames = "joinPoint,id,service")
    public void addLoggingBefore(JoinPoint joinPoint,
                                 Object id,
                                 Object service){
        log.debug("before = INVOKED FIND_BY_ID METHOD in class {}, with id {} ", service, id);
    }

    @AfterReturning(value = "getByIdServiceMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingReturning(Object result, Object service){
        log.debug("after returning = INVOKED FIND_BY_ID METHOD in class {}, result {} ", service, result);
    }

    @AfterThrowing(value = "getByIdServiceMethod() && target(service)", throwing = "ex")
    public void addLoggingThrowing(Throwable ex, Object service){
        log.debug("after throwing = INVOKED FIND_BY_ID METHOD in class {} exception{}: {}", service, ex.getClass(), ex.getMessage());
    }

    @After(value = "getByIdServiceMethod() && target(service)")
    public void addLoggingReturning(Object service){
        log.debug("after  = INVOKED FIND_BY_ID METHOD in class {}", service);
    }

    @Pointcut("execution(public * com.example.demowithtests.service.*Service.removeById(*))")
    public  void removeByIdServiceMethod(){}

    @Around(value = "removeByIdServiceMethod() && target(service) && args(id)")
    Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable {
        log.debug("AROUND before = INVOKED REMOVE_BY_ID METHOD in class {}, with id {}", service, id);
        try {
            Object result = joinPoint.proceed();
            log.debug("AROUND after returning = REMOVE_BY_ID METHOD in class {}, result {} ", service, result);
            return result;
        }catch (Throwable ex){
            log.debug("AROUND after throwing = INVOKED REMOVE_BY_ID METHOD in class {} exception{}: {}", service, ex.getClass(), ex.getMessage());
            throw ex;
        } finally {
            log.debug("AROUND after  = INVOKED REMOVE_BY_ID METHOD in class {}", service);
        }
    }
}
