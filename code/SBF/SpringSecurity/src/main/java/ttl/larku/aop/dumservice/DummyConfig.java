package ttl.larku.aop.dumservice;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableAspectJAutoProxy
public class DummyConfig {

    @Bean
    public DummyService dummyService() {
        return new DummyService();
    }

    @PostConstruct
    public void initLogger() {

        //suppose you use default logback (ch.qos.logback.classic.LoggerContext)
        LoggerContext c = (LoggerContext) LoggerFactory.getILoggerFactory();
        c.getLogger("ttl.larku").setLevel(Level.DEBUG);
        c.getLogger("org.springframework").setLevel(Level.DEBUG);
    }

}
