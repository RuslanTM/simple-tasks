package main.com.Collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
/**
 * Tasks connected with {@link LinkedList}
 */
public class LinkedLists {

  public static void main(String[] args) {
    /*LinkedList<String> list = new LinkedList<>();
    list.add("Hello");
    list.add("Hello");
    list.add("World!");
    list.add("Hello");
    list.add("Hello");
    list.add("World!");
    removeDuplicates(list);
    System.out.println(list);*/
    LinkedListNode node = new LinkedListNode(1);
    node.add(2);
    node.next.add(3);
    node.next.next.add(4);
    node.next.next.next.add(5);
    node.next.next.next.next.add(6);
    node.next.next.next.next.next.add(7);
    node.next.next.next.next.next.next.add(8);
    printKthToLast(node, 5);
    System.out.println(nthToLast(node, 5).data);
  }

  private static void removeDuplicates(LinkedList<String> list) {
    Set<String> set = new HashSet<>();
    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
      String s = iterator.next();
      if (set.contains(s)) {
        iterator.remove();
      } else {
        set.add(s);
      }
    }
  }

  private static int printKthToLast(LinkedListNode node, int k) {
    if (node.next == null) {
      return 0;
    }

    int index = printKthToLast(node.next, k) + 1;
    if (index == k) {
      System.out.println("Kth element to the last is " + node.data);
    }
    return index;
  }

  private static LinkedListNode nthToLast(LinkedListNode head, int k) {
    LinkedListNode p1 = head;
    LinkedListNode p2 = head;

    for (int i = 0; i < k; i++) {
      if (p1 == null) return null;
      p1 = p1.next;
    }

    while (p1 != null) {
      p1 = p1.next;
      p2 = p2.next;
    }
    return p2;
  }

  /**
   * My implementation of LinkedListNode.
   * @param <E> data type.
   */
  private static class LinkedListNode<E> {

    public LinkedListNode next;
    public E data;

    public LinkedListNode(E data) {
      this.data = data;
    }

    public void add(E data) {
      LinkedListNode node = new LinkedListNode(data);
      this.next = node;
    }
  }

}
