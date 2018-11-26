package com.vvwyy.cowpea.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.CacheManagerConfiguration;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class EhcacheTest {
    private static CacheManager cacheManager;

    @BeforeClass
    public static void beforeClass() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("test", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
    }

    @Test
    public void test() {
        Cache<String, String> cache = cacheManager.getCache("test", String.class, String.class);
        cache.put("demo", "DEMO");

        System.out.println(cache.get("demo"));
        System.out.println(cache.get("jj"));
    }

}
