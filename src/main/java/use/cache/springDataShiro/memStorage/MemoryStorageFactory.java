package use.cache.springDataShiro.memStorage;

import java.util.concurrent.ConcurrentMap;

import org.springframework.data.redis.cache.RedisCache;

public class MemoryStorageFactory 
{

	public static IMemoryStorage getMemoryStorage(org.springframework.cache.Cache cache)
	{
		IMemoryStorage bean = null;
		Object cacheObject = null;
		if(cache instanceof RedisCache)
		{
			bean = new MemoryStorageSpringRedis();	
			cacheObject = cache;
		}
		
		if(cache.getNativeCache() instanceof ConcurrentMap)
		{
			bean = new MemoryStorageMap();	
			cacheObject = cache.getNativeCache();
		}
		if(bean == null)
		{
			return null;
		}
		bean.setSpringCache(cacheObject);
		return bean;
	}
}
