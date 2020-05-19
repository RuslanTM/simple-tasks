package main.com.concurrency.waitNotify;

public class Drop {
  private String message;
  private boolean isEmpty = true;

  public synchronized String take() {
    while(isEmpty) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    isEmpty = true;
    notifyAll();
    return message;
  }

  public synchronized void put(String message) {
    while(!isEmpty) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    isEmpty = false;
    this.message = message;
    notifyAll();
  }
}
