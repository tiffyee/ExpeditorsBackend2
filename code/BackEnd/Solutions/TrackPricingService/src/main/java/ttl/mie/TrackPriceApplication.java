package ttl.mie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@SpringBootApplication
@PropertySource("classpath:/pricing.properties")
public class TrackPriceApplication {

   public static void main(String[] args) {
      SpringApplication.run(TrackPriceApplication.class, args);
   }
}

@Component
class StartupMessager implements CommandLineRunner {

   private Logger logger = LoggerFactory.getLogger(getClass());
   private final double lowerLimit;
   private final double upperLimit;

   public StartupMessager(@Value("${pricing.track.lower_limit}") double lowerLimit,
                          @Value("${pricing.track.upper_limit}") double upperLimit ) {
      this.lowerLimit = lowerLimit;
      this.upperLimit = upperLimit;
   }

   @Override
   public void run(String... args) throws Exception {
      logger.info("Starting TrackPriceApplication with limits: {}, {}", lowerLimit, upperLimit);
   }
}

