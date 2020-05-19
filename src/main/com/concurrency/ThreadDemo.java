package main.com.concurrency;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadDemo {

  public static void main(String[] args) throws InterruptedException {
    LightCruiser varyag = new LightCruiser();

    Runnable japanGleet = () -> {
      while(!Thread.currentThread().isInterrupted()) {
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          System.out.println("ERROR in japanFleet" + e.getMessage());
          Thread.currentThread().interrupt();
        }
        varyag.shoot();
        System.out.println("Varyag is damaged " + varyag.getHealth());
      }
    };

    Runnable vladivostok = () -> {
      while(!Thread.currentThread().isInterrupted()) {
        if(varyag.getHealth() < 50) {
          for (int i = 0; i < 3; i++) {
            System.out.println("Repairing " + varyag.getHealth());
            try {
              Thread.sleep(20);
            } catch (InterruptedException e) {
              System.out.println("ERROR in vladivostok " + e.getMessage());
              Thread.currentThread().interrupt();
            }
            varyag.repair();
          }
        }
      }
    };

    Thread t1 = new Thread(japanGleet);
    Thread t2 = new Thread(vladivostok);

    t1.start();
    t2.start();
    Thread.sleep(1000);
    t1.interrupt();
    t2.interrupt();
    t1.join();
    t2.join();
  }

  static class LightCruiser {
    private int health = 100;
    private boolean isDamaged;
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void repair() {
      lock.readLock().lock();
      if (isDamaged) {
        lock.readLock().unlock();
        lock.writeLock().lock();
        if (isDamaged) {
          health++;
          isDamaged = false;
        }
        lock.writeLock().unlock();
      } else {
        lock.readLock().unlock();
      }
    }

    public void shoot() {
      lock.writeLock().lock();
      health-=10;
      isDamaged = true;
      lock.writeLock().unlock();
    }

    public int getHealth() {
      lock.readLock().lock();
      int result = health;
      lock.readLock().unlock();
      return result;
    }

  }
}

