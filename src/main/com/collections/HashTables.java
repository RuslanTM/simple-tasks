package main.com.collections;

import java.util.ArrayList;

/**
 * Tasks connected with {@link java.util.HashMap}
 */
public class HashTables {

  public static void main(String[] args) {
    MyMap<String, String> myMap = new MyMap<>();
    myMap.put("1", "Hey nman");
    myMap.put("sadas", "Hey man");
    myMap.put("13e12", "Hey man");
    myMap.put("dsdf", "Hey man");
    myMap.put("Frederico", "Hey man");

    System.out.println(myMap.get("1"));
    System.out.println(myMap.size());
    System.out.println(myMap.isEmpty());
    myMap.remove("1");
    System.out.println(myMap.size());
  }


  static class HashNode<K, V> {

    K key;
    V value;

    HashNode<K, V> next;

    public HashNode(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  static class MyMap<K, V> {
    private ArrayList<HashNode<K, V>> bucketArray;
    private int numBuckets;
    private int size;

    public MyMap() {
      bucketArray = new ArrayList<>();
      numBuckets = 10;
      size = 0;

      for (int i = 0; i < numBuckets; i++) {
        bucketArray.add(null);
      }
    }

    public int size() {
      return size;
    }

    public boolean isEmpty() {
      return size == 0;
    }

    private int getBucketIndex(K key) {
      int hashCode = key.hashCode();
      int index = hashCode % numBuckets;
      return index;
    }

    public V remove(K key)  {
      int bucketIndex = getBucketIndex(key);
      HashNode<K, V> head = bucketArray.get(bucketIndex);
      HashNode<K, V> prev = null;
      while(head != null) {
        if (head.key.equals(key)) {
          break;
        }
        prev = head;
        head = head.next;
      }

      if (head == null) {
        return null;
      }

      size--;

      if (prev != null) {
        prev.next = head.next;
      } else {
        bucketArray.set(bucketIndex, head.next);
      }
      return head.value;
    }

    public V get(K key) {
      int bucketIndex = getBucketIndex(key);
      HashNode<K, V> head = bucketArray.get(bucketIndex);

      while (head != null) {
        if (head.key.equals(key)) {
          return head.value;
        }

        head = head.next;
      }
      return null;
    }

    public void put(K key, V value) {
      int bucketIndex = getBucketIndex(key);
      HashNode<K, V> head = bucketArray.get(bucketIndex);

      while (head != null) {
        if (head.key.equals(key)) {
          head.value = value;
          return;
        }
        head = head.next;
      }

      size++;
      head = bucketArray.get(bucketIndex);
      HashNode<K, V> newNode = new HashNode<>(key, value);
      newNode.next = head;
      bucketArray.set(bucketIndex, newNode);

      if (1.0 * size / numBuckets >= 0.7) {
        ArrayList<HashNode<K,V>> temp = bucketArray;
        bucketArray = new ArrayList<>();
        numBuckets = 2*numBuckets;
        size = 0;
        for (int i = 0; i < numBuckets; i++) {
          bucketArray.add(null);
        }

        for (HashNode<K,V> hashNode : temp) {
          while (hashNode != null) {
             put(hashNode.key, hashNode.value);
             hashNode = hashNode.next;
          }
        }
      }
    }
  }
}
