package com.itself.test.meituan;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 美团外包面试题
 * @Author duJi
 * @Date 2023/11/10
 */
public class LocalCacheAndConcurrent {
    public class ShopInfo{
        private Integer id;
        private String name;
        private String address;

        public ShopInfo(Integer id, String name, String address) {
            this.id = id;
            this.name = name;
            this.address = address;
        }
    }
    public class ShopService{
        //已经提供的方法1：批量根据商户id查询商户名称
        //限制：入参shopIds不大于10个
        public Map<Integer, String> getShopName(List<Integer> shopIds){
            return Collections.emptyMap();
        }
        //已经提供的方法1：批量根据商户id查询商户地址
        //限制：入参shopIds不大于10个
        public Map<Integer, String> getShopAddress(List<Integer> shopIds){
            return Collections.emptyMap();
        }
    }

    public class AService{
        //本地缓存方法
        private Map<Integer, ShopInfo> localCacheShopInfo = new ConcurrentHashMap<>();
        private ShopService shopService = new ShopService();

        /**
         * 目标：根据商户id批量查询商户信息，入参shopIds不大于100个
         * 要求：1.并行调用ShopService提供的getShopName和getShopAddress方法获取商户名称和地址，构造ShopInfo
         * 2.从ShopService中获取数据时，需要满足ShopService入参的shopIds不大于10个的条件限制，即需要分批调用
         * 3.实现本地缓存，优先从本地缓存中获取数据，如果本地缓存没有数据，再从ShopService中获取数据
         * @param shopIds
         * @return
         */
        public Map<Integer,ShopInfo> getShopInfo(List<Integer> shopIds){
            //初始化返回值
            Map<Integer, ShopInfo> result = new HashMap<>();
            //初始化不在缓存中的id
            List<Integer> unFoundIds = new ArrayList<>();
            //先判断本地缓存中是否有对应的id,有就仿佛返回结果集中
            shopIds.forEach(id->{
                if (localCacheShopInfo.containsKey(id)){
                    result.put(id, localCacheShopInfo.get(id));
                }else {
                    unFoundIds.add(id);
                }
            });
            //如果unFoundIds为空说明缓存里包含了所有商户id，并在上一步放进返回值中
            if (CollectionUtils.isEmpty(unFoundIds)){
                return result;
            }
            //有数据需要调用方法获取，分批调用ShopService获取商户名称和地址并初始化首批次的数量
            int batchSize = 10;
            for (int i = 0; i < unFoundIds.size(); i += batchSize) { //每次+10
                //利用函数取到unFoundIds的最后一位，可防止索引越界
                int endIndex = Math.min(i + batchSize, unFoundIds.size());
                //截取unFoundIds的批次数量，最后一次可能不足10个
                List<Integer> batchShopIds = unFoundIds.subList(i, endIndex);
                //调用ShopService的方法
                Map<Integer, String> shopNames = shopService.getShopName(batchShopIds);
                Map<Integer, String> shopAddresses = shopService.getShopAddress(batchShopIds);
                //封装ShopInfo对象并加入结果集和本地缓存
                batchShopIds.forEach(id->{
                    String name = shopNames.get(id);
                    String address = shopAddresses.get(id);
                    ShopInfo shopInfo = new ShopInfo(id, name, address);
                    //返回结果集
                    result.put(id, shopInfo);
                    //放入本地缓存
                    localCacheShopInfo.put(id, shopInfo);
                });
            }
            return result;
        }
    }
}
