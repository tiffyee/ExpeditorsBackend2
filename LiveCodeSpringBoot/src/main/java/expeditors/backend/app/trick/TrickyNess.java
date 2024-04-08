package expeditors.backend.app.trick;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

interface Trick {
   public void doTrick();
}

@Component
//@Primary
//@Profile("us-east")
@Qualifier("us-east")
class Trick1 implements Trick {
   @Override
   public void doTrick() {
      System.out.println("Handstand");
   }
}

@Component
@Qualifier("us-west")
//@Profile("us-west")
class Trick2 implements Trick {
   @Override
   public void doTrick() {
      System.out.println("Somersault");
   }
}

@Component
@Qualifier("us-west")
//@Profile("us-west")
class Trick3 implements Trick {
   @Override
   public void doTrick() {
      System.out.println("Somersault");
   }
}

@Component
class Circus
{
//   @Autowired
   private Trick trick;

   @Autowired
   @Qualifier("us-east")
   private List<Trick> eastTricks;

   @Autowired
   @Qualifier("us-west")
   private List<Trick> westTricks;

   public void startShow() {
//      trick.doTrick();
      westTricks.forEach(Trick::doTrick);
   }

   public static void main(String[] args) {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
      context.getEnvironment().setActiveProfiles("us-west", "blah" );
      context.scan("expeditors.backend.app.trick");
      context.refresh();

      Circus circus = context.getBean("circus", Circus.class);
      circus.startShow();
   }
}
