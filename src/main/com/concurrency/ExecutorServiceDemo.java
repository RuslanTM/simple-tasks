package main.com.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {

  public static void main(String[] args) {
    ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
    pool.schedule(() -> System.out.println("Ping google.com"), 2, TimeUnit.SECONDS);
    pool.scheduleAtFixedRate(() -> System.out.println("Ping yandex.ru"), 0, 2, TimeUnit.SECONDS);

    pool.scheduleWithFixedDelay(() -> System.out.println("Ping amazon.com"), 3, 2, TimeUnit.SECONDS);
    try {
      pool.awaitTermination(15, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      pool.shutdownNow();
    }
  }

  public static void showExecutorServiceWork() {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    Runnable task1  = () -> {
      System.out.println("Hello from task1 " + Thread.currentThread().getName());
    };

    Runnable task2 = () -> {
      try {
        Thread.sleep(4000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Hello from task2 " + Thread.currentThread().getName());
    };

    executorService.submit(task1);
    executorService.submit(task2);
    try{
      executorService.shutdown();
      executorService.awaitTermination(1, TimeUnit.SECONDS);
    } catch(Exception e) {
      System.out.println(e.getMessage());
    } finally {
      executorService.shutdownNow();
    }
    System.out.println("Hello from main " + Thread.currentThread().getName());
  }
}
