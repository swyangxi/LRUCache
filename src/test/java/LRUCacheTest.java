import cache.LRUCache;
import exception.ErrorInfo;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class LRUCacheTest {

    /**
     * 添加新数据场景
     */
    @Test
    public void testAdd(){
        LRUCache<Integer, Integer> lruCache = new LRUCache<Integer, Integer>(3);
        lruCache.put(1,1);
        lruCache.put(2,2);
        assertNull(lruCache.get(3));
        lruCache.put(3,3);
        assertEquals(lruCache.get(3).intValue(),3);
        assertEquals(lruCache.size(),3);
    }

    /**
     * 添加相同元素场景，测试是否淘汰
     */
    @Test
    public void testAddSameData(){
        LRUCache<Integer, Integer> lruCache = new LRUCache<Integer, Integer>(3);
        lruCache.put(1,1);
        assertEquals(lruCache.get(1).intValue(),1);
        lruCache.put(2,2);
        lruCache.put(3,3);
        lruCache.put(1,1);
        lruCache.put(1,1);
        assertNotNull(lruCache.get(1));
        assertEquals(lruCache.size(),3);

    }
    /**
     * 队列满淘汰队尾场景
     */
    @Test
    public void testFullQueue(){
        LRUCache<Integer, Integer> lruCache = new LRUCache<Integer, Integer>(4);
        lruCache.put(1,1);
        assertEquals(lruCache.get(1).intValue(),1);
        lruCache.put(2,2);
        lruCache.put(3,3);
        lruCache.put(4,4);
        lruCache.put(5,5);
        assertNull(lruCache.get(1));
        assertEquals(lruCache.size(),4);

    }

    /**
     * 初始化长度为0场景
     */
    @Test
    public void testCapacityZero(){
        LRUCache<Integer, Integer> lruCache = new LRUCache<Integer, Integer>(0);
        lruCache.put(1,1);
        lruCache.put(2,2);
        assertNull(lruCache.get(1));
        assertNull(lruCache.get(2));

    }

    /**
     * 非法长度场景
     */
    @Test
    public void throwsCapacityLessThanZeroException(){
        try {
            LRUCache<Integer, Integer> lruCache = new LRUCache<Integer, Integer>(-1);
            lruCache.put(1,1);
        }catch (Exception e){
            assertEquals(e.getMessage(), ErrorInfo.CAPACITY_LESS_THAN_ZERO.getKey());
        }
    }

    /**
     * 非法入参
     */
    @Test
    public void throwsNullArgumentException(){
        try {
            LRUCache<Integer, Integer> lruCache = new LRUCache<Integer, Integer>(10);
            lruCache.put(null,null);
        }catch (Exception e){
            assertEquals(e.getMessage(), ErrorInfo.NULL_ARGUMENT.getKey());
        }
    }


}
