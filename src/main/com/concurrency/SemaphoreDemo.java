package main.com.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

  public static void main(String[] args) {
    Semaphore writingSemaphore = new Semaphore(50);
    Semaphore readingSemaphore = new Semaphore(10);

    List<Thread> threads = new ArrayList<>();
    List<Object> resource = new ArrayList<>();

    for (int i=0; i <1000; i++) {
      Thread writer = new Thread(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + " is waiting writing permit");
          writingSemaphore.acquire();
          System.out.println(Thread.currentThread().getName() + " has writing permit");
          resource.add(new Object());
        } catch(InterruptedException e) {
          e.printStackTrace();
        } finally {
          System.out.println("Goodbye from " + Thread.currentThread().getName());
          writingSemaphore.release();
        }
      });
      writer.setName("Writer #" + i);
      threads.add(writer);
    }

    for (int i = 0; i < 100; i++) {
      Thread reader = new Thread(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + " is waiting reading permit");
          readingSemaphore.acquire();
          System.out.println(Thread.currentThread().getName() + " has reading permit");
          System.out.println("Number of elements: " + resource.size());
        }
        catch(InterruptedException e) {
          e.printStackTrace();
        }
        finally {
          readingSemaphore.release();
          System.out.println("Goodbye from " + Thread.currentThread().getName());
        }
      });
      reader.setName("Reader #" + i);
      threads.add(reader);
    }

    threads.forEach(Thread::start);
    threads.forEach((thread) -> {
      try {
        thread.join();
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    });
    System.out.println("Result: " + resource.size());
  }

}
