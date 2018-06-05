package use.cache.springDataRedis.redis;

import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

import use.cache.springDataRedis.initial.SpringRedisFactoryUtil;

@SuppressWarnings("all")
public abstract class RedisClientAbstract {

	private int dataBaseIndex = 0;
	private volatile RedisTemplate redisTemp = null;
	
	public RedisClientAbstract(final int index)
	{
		dataBaseIndex = index;
	}
	public abstract RedisTemplate initalRedisTemplate(RedisConnectionFactory con);
	
	
	private RedisConnectionFactory getConnection()
	{
		switch(dataBaseIndex) 
		{
			case 0: return SpringRedisFactoryUtil.getMem();
			case 1: return SpringRedisFactoryUtil.getMem1();
			case 2: return SpringRedisFactoryUtil.getMem2();
			default: return SpringRedisFactoryUtil.getMem();
		}
	}
	public RedisTemplate getRedisTemplate()
	{
		if(redisTemp == null)
		{
			synchronized (RedisClientAbstract.class){
				if(redisTemp == null)
				{
					redisTemp = initalRedisTemplate(getConnection());
				}
			}
		}
		return redisTemp;
	}
	
	public Object get(Object key) throws CacheException 
	{
		try 
		{
			return getRedisTemplate().opsForValue().get(key);
		}finally
		{
			RedisConnectionUtils.unbindConnection(getRedisTemplate().getConnectionFactory());
		}
	}
	
	public Object set(final Object key, final Object value)
	{
		return set(key , value);
	}
	/**
	 * 保存对象
	 * @param key
	 * @param value
	 * @param expire
	 * @param time
	 * @return
	 */
	public Object set(final Object key, final Object value, final long expire ,final TimeUnit time) 
	{	
		try
		{
			if(expire != 0)
			{
				getRedisTemplate().opsForValue().set(key, value, expire, time);
			} else {
				getRedisTemplate().opsForValue().set(key , value);
			}
			return  value;
		} finally
		{
			RedisConnectionUtils.unbindConnection(getRedisTemplate().getConnectionFactory());
		}
	}
	
	/**
	 * 缓存中读取对象，如果不存在则将获取对象并存储到缓存
	 * @param key 缓存对象key
	 * @param info 读取需要缓存的对象方法 
	 * @return 
	 * @return 缓存对象
	 */
	public <T> T cache(final String key  , final IRedisReadInfo<T> info)
	{
		return cache(key , 0 , null , info);
	}
	/**
	 * 缓存中读取对象，如果不存在则将获取对象并存储到缓存
	 * @param <T>
	 * @param key 缓存对象key
	 * @param expire 有效时长
	 * @param time 时间单位
	 * @param info 读取需要缓存的对象方法
	 * @return
	 */
	public <T> T cache(final String key , final long expire ,final TimeUnit time , final IRedisReadInfo<T> info)
	{
		try
		{
			Object r = getRedisTemplate().opsForValue().get(key);
			if(r == null)
			{
				synchronized(RedisClientAbstract.class)
				{
					r = getRedisTemplate().opsForValue().get(key);
					if(r == null)
					{
						r = info.readInfo();
						if(expire != 0)
						{
							getRedisTemplate().opsForValue().set(key, r, expire, time);
						} else {
							getRedisTemplate().opsForValue().set(key , r);
						}
					}
				}
			}			
			return (T)r;
		} finally
		{
			RedisConnectionUtils.unbindConnection(getRedisTemplate().getConnectionFactory());
		}
	}
}
