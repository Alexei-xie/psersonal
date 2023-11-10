package com.itself.test.meituan;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author duJi
 * @Date 2023/11/10
 */
public class Cache<K,V> {
    private Map<K, V> cache;
    private ExecutorService executor;

    public Cache() {
        this.cache = new ConcurrentHashMap<>();
        this.executor = Executors.newFixedThreadPool(10);//构造函数初始固定线程池数量
    }

    public V get(K key) {
        V value = cache.get(key);
        if (value != null) {
            return value;
        }
        //缓存过期，利用异步编排去执行更新操作并放回缓存种
        CompletableFuture.supplyAsync(() -> update(key), executor);
        return value; // 返回上一次的结果
    }

    private V update(K key) {
        // 调用 AService.get(K) 接口获取实时数据
        // V v = AService.get(K);
        V v = null;
        //直接覆盖缓存中的数据
        cache.put(key,v);
        return v;
    }
}



