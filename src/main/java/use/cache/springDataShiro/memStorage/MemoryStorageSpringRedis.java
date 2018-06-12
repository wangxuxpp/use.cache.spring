package use.cache.springDataShiro.memStorage;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;

import use.cache.springDataRedis.initial.CacheManagerOperation;
import use.cache.springDataRedis.initial.SpringRedisFactoryUtil;
import use.cache.springDataRedis.initial.SpringRedisTemplateUtil;


@SuppressWarnings({"rawtypes","unchecked"})
public class MemoryStorageSpringRedis implements IMemoryStorage {

	private RedisCache mem = null;
	private RedisTemplate rt = null;
	

	public Object getSpringCache() {
		return mem;
	}

	public void setSpringCache(Object cache) {
		if(cache instanceof RedisCache)
		{
			mem = ((RedisCache)cache);
			if(mem.getName().equals(CacheManagerOperation.defaultCache))
			{
				rt = SpringRedisTemplateUtil.getObjectRedisTemplate(SpringRedisFactoryUtil.getMem());
			}
			if(mem.getName().equals(CacheManagerOperation.secondCache))
			{
				rt = SpringRedisTemplateUtil.getObjectRedisTemplate(SpringRedisFactoryUtil.getMem1());
			}
			if(mem.getName().equals(CacheManagerOperation.limitsCache))
			{
				rt = SpringRedisTemplateUtil.getObjectRedisTemplate(SpringRedisFactoryUtil.getMem2());
			}
		}
	}
	


	public Object get(Object key) throws CacheException 
	{
		try 
		{
			return rt.opsForValue().get(key);
		}finally
		{
			//RedisConnectionUtils.unbindConnection(rt.getConnectionFactory());
		}
	}

	public Object put(Object key, Object value) throws CacheException {
		try 
		{
			mem.putIfAbsent(key, value);
		}catch(Exception er)
		{
			throw new CacheException(er.getMessage());
		}
		return value;
	}

	public Object remove(Object key) throws CacheException {
		try
		{
			return rt.delete(key);
		}finally
		{
			//RedisConnectionUtils.unbindConnection(rt.getConnectionFactory());
		}
	}

	public void clear() throws CacheException {
		mem.clear();
	}

	public int size() 
	{
		
		try
		{
			return rt.keys("*").size();
		}finally 
		{
			//RedisConnectionUtils.unbindConnection(rt.getConnectionFactory());
		}
	}

	public Set keys(String key) 
	{
		
		try
		{
			return rt.keys(key);
		}finally
		{
			//RedisConnectionUtils.unbindConnection(rt.getConnectionFactory());
		}
	}

	public Collection values(String key) {
		
		try
		{
			return rt.keys(key);
		}finally
		{
			//RedisConnectionUtils.unbindConnection(rt.getConnectionFactory());
		}
	}

	
	public Object set(Object key, Object value, int expire) throws Exception {
		
		
		try
		{
			if(expire != 0)
			{
				rt.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
			} else {
				rt.opsForValue().set(key , value);
			}
			return  value;
		} finally
		{
			//RedisConnectionUtils.unbindConnection(rt.getConnectionFactory());
		}
		
	}

}
