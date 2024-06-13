package org.ttl.ratingservice.app;

public class ThreadDemo {

   public static void main(String[] args) {
      ThreadDemo threadDemo = new ThreadDemo();
      for(int i = 0 ; i < 10; i++) {
         threadDemo.go();
      }
   }

   public void go() {
      DataHolder dh = new DataHolder();
      Worker w1 = new Worker(dh);
      Thread th1 = new Thread(w1);

      Worker w2 = new Worker(dh);
      Thread th2 = new Thread(w2);

      th1.start();
      th2.start();

      try {
         th1.join();
         th2.join();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }


      System.out.println("At end Value is: " + dh.getNextValue());
      System.out.println("Main Thread is done: " + Thread.currentThread().getName());

   }

   public class Worker implements Runnable {
      private DataHolder dataHolder;

      public Worker(DataHolder dataHolder) {
         this.dataHolder = dataHolder;
      }

      @Override
      public void run() {
         for(int i = 0; i < 1000; i++) {
            int value = dataHolder.getNextValue();

            //do something with the value
         }
      }
   }
}

class DataHolder
{
   private int value;
   private int otherValue;

//   private AtomicInteger value = new AtomicInteger(0);

   public int getNextValue() {
      int x = 0;
      synchronized (this) {
         x = value++;
         otherValue = x * x;
      }
      return x;
//      return value.getAndIncrement();
   }

   public int getValue(int seed) {
      return seed * 2;
   }
}
