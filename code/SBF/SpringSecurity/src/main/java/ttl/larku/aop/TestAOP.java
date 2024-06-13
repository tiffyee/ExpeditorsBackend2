package ttl.larku.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ttl.larku.aop.dumservice.DummyService;

public class TestAOP {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("development");
        ctx.scan("ttl.larku.aop");
        ctx.refresh();

        DummyService ds = ctx.getBean("dummyService", DummyService.class);
        String result = ds.getDummy("franky");

        System.out.println(result);
    }
}
