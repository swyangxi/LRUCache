package cache;

public class Node<K,V> {
    K key;
    V val;
    Node next;
    Node pre;
    Node(){};
    Node(K key,V val){
        this.key=key;
        this.val=val;
    }
}
