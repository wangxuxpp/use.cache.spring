package use.cache.springDataRedis.initial;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;


public class CacheManagerOperation {

	protected final static Logger log = LoggerFactory.getLogger(CacheManagerOperation.class);
	private volatile static AbstractCacheManager fCache = null;
	
	public static final String defaultCache = "default";
	public static final String secondCache = "redisDataBaseSecondName";
	public static final String limitsCache = "limitsCache";
	
	public static AbstractCacheManager getCacheManager()
	{
		log.info("系统缓存 ----initial start");
		if (fCache != null)
		{
			return fCache;
		} 
		
		if (SpringRedisFactoryUtil.getConfigure().isRedisEanble())
		{
			//default 缓存配置
			RedisCacheConfiguration arcc = RedisCacheConfiguration.defaultCacheConfig();
			arcc.entryTtl(Duration.ofDays(30));
			arcc.disableCachingNullValues();
			arcc.prefixKeysWith(CacheManagerOperation.defaultCache);
			//redisDataBaseSecondName 缓存配置
			RedisCacheConfiguration brcc = RedisCacheConfiguration.defaultCacheConfig();
			brcc.entryTtl(Duration.ofMinutes(30));
			brcc.disableCachingNullValues();
			brcc.prefixKeysWith(CacheManagerOperation.secondCache);
			//limitsCache 缓存配置
			RedisCacheConfiguration crcc = RedisCacheConfiguration.defaultCacheConfig();
			crcc.entryTtl(Duration.ofDays(30));
			crcc.disableCachingNullValues();
			crcc.prefixKeysWith(CacheManagerOperation.limitsCache);
			
			Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<String, RedisCacheConfiguration>();
			redisCacheConfigurationMap.put(CacheManagerOperation.defaultCache, arcc);
			redisCacheConfigurationMap.put(CacheManagerOperation.secondCache, brcc);
			redisCacheConfigurationMap.put(CacheManagerOperation.limitsCache, crcc);
					
			RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(SpringRedisFactoryUtil.getMem());
			
			RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
			defaultCacheConfig.entryTtl(Duration.ofDays(30));
			defaultCacheConfig.disableCachingNullValues();
			defaultCacheConfig.prefixKeysWith(CacheManagerOperation.defaultCache);
			RedisCacheManager c = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, redisCacheConfigurationMap);
			fCache = c;
			log.info("系统缓存类型： redis");	
			
		} else {
			fCache = new SimpleCacheManager();
			List<Cache> l = new ArrayList<Cache>();
			/*ConcurrentMapCacheFactoryBean c = new ConcurrentMapCacheFactoryBean();
			c.setBeanName(CacheManagerOperation.cacheName);
			c.setName(CacheManagerOperation.cacheName);*/
			ConcurrentMapCache c =new ConcurrentMapCache(CacheManagerOperation.defaultCache);
		    l.add(c);
		    ConcurrentMapCache d =new ConcurrentMapCache(CacheManagerOperation.secondCache);
		    l.add(d);
		    ConcurrentMapCache e =new ConcurrentMapCache(CacheManagerOperation.limitsCache);
		    l.add(e);
		    log.info("系统缓存类型： map");
		    ((SimpleCacheManager) fCache).setCaches(l);
		    
		}
		log.info("系统缓存 ----initial end");
		return fCache;
	}
}
