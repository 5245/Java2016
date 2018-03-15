package com.sxk.cache.ch1;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class AppkeyInfoLocalCache {
    static class AppkeyInfoBasic {

    }

    static LoadingCache<String, AppkeyInfoBasic> cache = CacheBuilder.newBuilder().refreshAfterWrite(3, TimeUnit.SECONDS)// 给定时间内没有被读/写访问，则回收。
                                                               .expireAfterAccess(60, TimeUnit.SECONDS)// 缓存过期时间和redis缓存时长一样
                                                               .maximumSize(1000).// 设置缓存个数
                                                               build(new CacheLoader<String, AppkeyInfoBasic>() {
                                                                   @Override
                                                                   /** 当本地缓存命没有中时，调用load方法获取结果并将结果缓存 **/
                                                                   public AppkeyInfoBasic load(String appKey) throws Exception {
                                                                       return getAppkeyInfo(appKey);
                                                                   }

                                                                   /** 数据库进行查询 **/
                                                                   private AppkeyInfoBasic getAppkeyInfo(String appKey) throws Exception {
                                                                       return null;
                                                                   }
                                                               });

    public static AppkeyInfoBasic getAppkeyInfoByAppkey(String appKey) throws Exception {
        return cache.get(appKey);
    }

}
