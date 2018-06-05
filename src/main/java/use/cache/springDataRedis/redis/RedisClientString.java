package use.cache.springDataRedis.redis;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import use.cache.springDataRedis.initial.SpringRedisTemplateUtil;
import use.common.exception.SystemException;
import use.common.json.JacksonToPojo;

public class RedisClientString extends RedisClientAbstract{

	public RedisClientString(int index) {
		super(index);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RedisTemplate initalRedisTemplate(RedisConnectionFactory con) {
		return SpringRedisTemplateUtil.getStringRedisTemplate(con);
	}
	
	public <T> T getObject(String key , Class<T> valueType ) 
	{
		String r = get(key).toString();
		try {
			return JacksonToPojo.getJackson().readValue(r, valueType);
		} catch (IOException e) {
			throw new SystemException(e);
		}
	}
	public Object set(final Object key, final Object value)
	{
		return this.set(key, value, 0, null);
	}
	public Object set(final Object key, final Object value, final long expire ,final TimeUnit time) 
	{	
		try
		{
			String v = JacksonToPojo.getJackson().writeValueAsString(value);
			return super.set(key, v, expire, time);
		} 
		catch(Exception er){
			throw new SystemException(er);
		}

	}
	
	public <T>T  cache(final String key , final IRedisReadInfo<T> info)
	{
		return this.cache(key, 0 , null ,  info);
	}
	
	public <T>T  cache(final String key , final long expire ,final TimeUnit time , final IRedisReadInfo<T> info)
	{
		String r = super.cache(key, expire, time, new IRedisReadInfo<String>() {
			@Override
			public String readInfo() {
				String v;
				try {
					v = JacksonToPojo.getJackson().writeValueAsString(info.readInfo());
				} catch (JsonProcessingException e) {
					throw new SystemException(e);
				}
				return v;
			}
			
		}); 
		try {
			return JacksonToPojo.getJackson().readValue(r, info.getTemplateClass());
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
}
