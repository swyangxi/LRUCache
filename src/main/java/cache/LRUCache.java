package cache;

import exception.CapacityException;
import exception.ErrorInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K,V> {

    private int  capacity;
    private Node<K,V> head;
    private Node<K,V>  tail;
    private Map<K, Node<K,V>> cacheMap =null;
    //读写锁，保证并发安全
    private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    private Lock writeLock=lock.writeLock();
    private Lock readLock=lock.readLock();

    public LRUCache(int capacity){
        if(capacity<0){
            throw new CapacityException(ErrorInfo.CAPACITY_LESS_THAN_ZERO.getKey());
        }
        this.capacity=capacity;
        cacheMap=new ConcurrentHashMap<>(capacity);
        //头尾两个哨兵节点
        head=new Node(null,null);
        tail=new Node(null,null);
        head.next=tail;
    }

    public int size(){
        readLock.lock();
        int size= cacheMap.size();
        readLock.unlock();;
        return size;
    }

    /**
     * 获取节点，移至头部
     */
    public V get(K key){
        if(key==null){
            throw new IllegalArgumentException(ErrorInfo.NULL_ARGUMENT.getKey());
        }
        writeLock.lock();
        Node result=cacheMap.get(key);
        if(result==null){
            return null;
        }
        removeNode(result);
        addToHead(result);
        writeLock.unlock();
        return (V)result.val;
    }

    /**
     * Map中有值则直接替换val，节点移至头部
     * 无值先添加到容器，然后做是否淘汰的计算
     */
    public void put(K key,V val){
        if(key==null||val==null){
            throw new IllegalArgumentException(ErrorInfo.NULL_ARGUMENT.getKey());
        }
        writeLock.lock();
        Node<K,V> node =cacheMap.get(key);
        if(node==null){
            node=new Node<K, V>(key,val);
            addToHead(node);
            cacheMap.put(key,node);
            if (size() > capacity) {
                //数量超过阈值，淘汰尾部节点
                cacheMap.remove(tail.pre.key);
                removeNode(tail.pre);
            }
        }else {
            //数据存在，移至头节点
            node.val=val;
            removeNode(node);
            addToHead(node);
        }
        writeLock.unlock();
    }

    private void removeNode(Node <K,V> node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }
    private void addToHead(Node node) {
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }
}
