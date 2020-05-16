package main.com;

import static java.lang.Math.pow;

public class Bud {

  public static void main(String[] args) {
    findPossibleValues(10);
  }

  public static void findPossibleValues(int n) {
    int count = 0;
    for (int a = 1; a <= n; a++) {
      for (int b = 1; b <= n; b++) {
        for (int c = 1; c <= n; c++) {
          int d = (int) (pow(pow(a, 3.0) + pow(b, 3.0) - pow(c, 3.0), 1 / 3));
          if (pow(a, 3.0) + pow(b, 3.0) == pow(c, 3.0) + pow(d, 3.0)) {
            count++;
            System.out.println(String.format("a = %d b = %d c = %d d = %d", a, b, c, d));
          }
        }
      }
    }
    System.out.println("Count = " + count);
  }
}
