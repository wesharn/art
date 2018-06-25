import com.google.common.cache.*;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */

public class GuavaCache {
    @Test
    public void TestLoadingCache() throws Exception{
        LoadingCache<String,String> cahceBuilder= CacheBuilder
                .newBuilder()
                .build(new CacheLoader<String, String>(){
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue="hello "+key+"!";
                        return strProValue;
                    }

                });

        System.out.println("jerry value:"+cahceBuilder.get("jerry"));
/*        cahceBuilder.put("harry", "ssdded");
        System.out.println("harry value:"+cahceBuilder.get("harry"));*/
        cahceBuilder.put("marry", "nnnn");
        System.out.println("marry value:"+cahceBuilder.get("marry", new Callable<String>() {
            public String call() {
                String strProValue="call "+"marry"+"!";
                return strProValue;
            }
        }));

        cahceBuilder.asMap();
    }
    public void testcallableCache()throws Exception{
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        String resultVal = cache.get("jerry", new Callable<String>() {
            public String call() {
                String strProValue="hello "+"jerry"+"!";
                return strProValue;
            }
        });
        System.out.println("jerry value : " + resultVal);

        resultVal = cache.get("peida", new Callable<String>() {
            public String call() {
                String strProValue="hello "+"peida"+"!";
                return strProValue;
            }
        });
        System.out.println("peida value : " + resultVal);
    }

    /**
     * 不需要延迟处理(泛型的方式封装)
     * @return
     */
    public  <K , V> LoadingCache<K , V> cached(CacheLoader<K , V> cacheLoader) {
        LoadingCache<K , V> cache = CacheBuilder
                .newBuilder()
                .maximumSize(2)
                .weakKeys()
                .softValues()
                .refreshAfterWrite(120, TimeUnit.SECONDS)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener(new RemovalListener<K, V>(){
                    @Override
                    public void onRemoval(RemovalNotification<K, V> rn) {
                        System.out.println(rn.getKey()+"被移除");

                    }})
                .build(cacheLoader);
        return cache;
    }

    /**
     * 通过key获取value
     * 调用方式 commonCache.get(key) ; return String
     * @param key
     * @return
     * @throws Exception
     */

    public  LoadingCache<String , String> commonCache(final String key) throws Exception{
        LoadingCache<String , String> commonCache= cached(new CacheLoader<String , String>(){
            @Override
            public String load(String key) throws Exception {
                return "hello "+key+"!";
            }
        });
        return commonCache;
    }

    public void testCache() throws Exception{
        LoadingCache<String , String> commonCache=commonCache("peida");
        System.out.println("peida:"+commonCache.get("peida"));
        commonCache.get("harry");
        System.out.println("harry:"+commonCache.get("harry"));
        commonCache.get("lisa");
        System.out.println("lisa:"+commonCache.get("lisa"));
    }


    private static Cache<String, String> cacheFormCallable = null;


    /**
     * 对需要延迟处理的可以采用这个机制；(泛型的方式封装)
     * @param <K>
     * @param <V>
     * @return V
     * @throws Exception
     */
    public static <K,V> Cache<K , V> callableCached() throws Exception {
        Cache<K, V> cache = CacheBuilder
                .newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                . removalListener(RemovalListeners.asynchronous(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> rn) {
                        System.out.println(rn.getKey()+"被移除");

                    }
                }, Executors.newSingleThreadExecutor()))
                .build();
        return cache;
    }


    private String getCallableCache(final String userName) {
        try {
            //Callable只有在缓存值不存在时，才会调用
            return cacheFormCallable.get(userName, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(userName+" from db");
                    return "hello "+userName+"!";
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void testCallableCache() throws Exception{
        final String u1name = "peida";
        final String u2name = "jerry";
        final String u3name = "lisa";
        cacheFormCallable=callableCached();
        System.out.println("peida:"+getCallableCache(u1name));
        System.out.println("jerry:"+getCallableCache(u2name));
        System.out.println("lisa:"+getCallableCache(u3name));
        System.out.println("peida:"+getCallableCache(u1name));

    }

    /**
     * 令牌桶限流（QPS），信号量限流（总的并发数）
     */
    public static void testWithRateLimiter() {
        Long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(10.0); // 每秒不超过10个任务被提交
        for (int i = 0; i < 10; i++) {
            limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞
            System.out.println("call execute.." + i);

        }
        Long end = System.currentTimeMillis();

        System.out.println(end - start);

    }
    /**
     * guava Cache数据移除：
     *　guava做cache时候数据的移除方式，在guava中数据的移除分为被动移除和主动移除两种。
     　　被动移除数据的方式，guava默认提供了三种方式：
     　　1.基于大小的移除:看字面意思就知道就是按照缓存的大小来移除，如果即将到达指定的大小，那就会把不常用的键值对从cache中移除。
     　　    定义的方式一般为 CacheBuilder.maximumSize(long)，还有一种一种可以算权重的方法，个人认为实际使用中不太用到。就这个常用的来看有几个注意点，
     　　　　其一，这个size指的是cache中的条目数，不是内存大小或是其他；
     　　　　其二，并不是完全到了指定的size系统才开始移除不常用的数据的，而是接近这个size的时候系统就会开始做移除的动作；
     　　　　其三，如果一个键值对已经从缓存中被移除了，你再次请求访问的时候，如果cachebuild是使用cacheloader方式的，那依然还是会从cacheloader中再取一次值，如果这样还没有，就会抛出异常
     　　2.基于时间的移除：guava提供了两个基于时间移除的方法
     　　　　expireAfterAccess(long, TimeUnit)  这个方法是根据某个键值对最后一次访问之后多少时间后移除
     　　　　expireAfterWrite(long, TimeUnit)  这个方法是根据某个键值对被创建或值被替换后多少时间移除
     　　3.基于引用的移除：
         　　这种移除方式主要是基于java的垃圾回收机制，根据键或者值的引用关系决定移除
         　　主动移除数据方式，主动移除有三种方法：
         　　1.单独移除用 Cache.invalidate(key)
         　　2.批量移除用 Cache.invalidateAll(keys)
         　　3.移除所有用 Cache.invalidateAll()
     *  　　如果需要在移除数据的时候有所动作还可以定义Removal Listener，但是有点需要注意的是默认Removal Listener中的行为是和移除动作同步执行的，如果需要改成异步形式，可以考虑使用RemovalListeners.asynchronous(RemovalListener, Executor)
     */
}
