package ttl;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadDemo {

   public static void main(String[] args) {
      ThreadDemo td = new ThreadDemo();
      td.go();
   }

   public void go() {
      CommonThing ct = new CommonThing();
      Worker worker1 = new Worker(ct);
      Worker worker2 = new Worker(ct);

      Thread th1 = new Thread(worker1);
      Thread th2 = new Thread(worker2);
      th1.start();
      th2.start();

      try {
         th1.join();
         th2.join();
      }catch(InterruptedException ex) {
         ex.printStackTrace();
      }

      System.out.println("i: " + ct.i + ", ct.ai: " + ct.ai);
   }


   class Worker implements Runnable {

      private final CommonThing th;

      public Worker(CommonThing th) {
         this.th = th;
      }

      @Override
      public void run() {
         for(int i = 0; i < 5000; i++) {
//            th.fun();
            System.out.println(Thread.currentThread() + ": " + th.fun());
         }
      }
   }


   class CommonThing {
      AtomicInteger ai = new AtomicInteger(0);
      int i = 0;
      public LocalDate fun() {
         i++;
         ai.getAndIncrement();
         return LocalDate.now();
      }
   }
}
