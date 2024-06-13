package ttl.larku.aop;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//@Component
public class ServiceTimingWithAspects {

    public ServiceTimingWithAspects() {
        int i = 0;
    }

    @Around("execution(* ttl.larku.aop.dumservice.*.*(..))")
    public Object timeDummyCall(ProceedingJoinPoint pjp) throws Throwable {
        Instant start = Instant.now();
        System.out.println("Before Call in ServiceTimingWithAspcect::timeDummyCall");

        Object retVal = pjp.proceed();


        System.out.printf("After AOP Call to %s took %d ms%n",
                pjp.getSignature(), start.until(Instant.now(), ChronoUnit.MILLIS));
        return retVal;
    }

    @Around("execution(* ttl.larku.service.*.*(..))")
    public Object timeCall(ProceedingJoinPoint pjp) throws Throwable {
        Instant start = Instant.now();
        System.out.println("Before Calli in ServiceTimingWithAspect::timeCall");

        Object retVal = pjp.proceed();

        System.out.printf("After AOP timeCall to %s took %d ms%n",
                pjp.getSignature(), start.until(Instant.now(), ChronoUnit.MILLIS));
        return retVal;
    }
}
