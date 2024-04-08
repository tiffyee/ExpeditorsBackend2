package ttl;

import java.time.LocalDate;

public class ThreadDemo {

   public static void main(String[] args) {
      ThreadDemo td = new ThreadDemo();
      td.go();
   }

   public void go() {
      CommonThing ct = new CommonThing();
      Worker worker1 = new Worker(ct);
      Worker worker2 = new Worker(ct);

      new Thread(worker1).start();
      new Thread(worker2).start();
   }


   class Worker implements Runnable {

      private final CommonThing th;

      public Worker(CommonThing th) {
         this.th = th;
      }

      @Override
      public void run() {
         while(true) {
            System.out.println(Thread.currentThread() + ": " + th.fun());
            try {
               Thread.sleep(500);
            } catch (InterruptedException e) {
               throw new RuntimeException(e);
            }
         }
      }
   }


   class CommonThing {
      public LocalDate fun() {
         return LocalDate.now();
      }
   }
}
