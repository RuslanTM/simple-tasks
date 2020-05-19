package main.com.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import sun.font.Type1GlyphMapper;

public class Threads {

  public String resource1 = "Resource1";
  public String resource2 = "Resource2";
  static int counter;
  static AtomicInteger atomicCounter = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {

    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 1_000_000; i++) {
        atomicCounter.getAndIncrement();
      }
    });

    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 1_000_000; i++) {
        atomicCounter.getAndDecrement();
      }
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(atomicCounter);
  }

  public static void makeDeadlock() throws InterruptedException {
    Threads threads = new Threads();

    Thread thread1 = new Thread(() -> {
      synchronized (threads.resource1) {
        System.out.println("Lock resource1 in " + Thread.currentThread().getName());
        try {
          Thread.sleep(100);
          synchronized (threads.resource2) {
            System.out.println("Lock resource2 in " + Thread.currentThread().getName());
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    Thread thread2 = new Thread(() -> {
      synchronized (threads.resource2) {
        System.out.println("Lock resource2 in " + Thread.currentThread().getName());

        try {
          Thread.sleep(100);
          synchronized (threads.resource1) {
            System.out.println("Lock resource1 in " + Thread.currentThread().getName());
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();
  }

  public static void makeJoin() throws InterruptedException {
    Thread t1 = new Thread(() -> {
      System.out.println("I am " + Thread.currentThread().getName());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    Thread t2 = new Thread(() -> {
      System.out.println("I am " + Thread.currentThread().getName());
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    t1.start();
    t2.start();

    t1.join();
    t2.join();
    System.out.println("End");
  }

  public static void countWithLock() throws InterruptedException {
    Lock lock = new ReentrantLock();

    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 1_000_000; i++) {
        lock.lock();
        counter++;
        lock.unlock();
      }
    });

    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 1_000_000; i++) {
        lock.lock();
        counter--;
        lock.unlock();
      }
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(counter);
  }

}
