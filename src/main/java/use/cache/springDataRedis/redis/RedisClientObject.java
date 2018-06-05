package use.cache.springDataRedis.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import use.cache.springDataRedis.initial.SpringRedisTemplateUtil;

public class RedisClientObject extends RedisClientAbstract{

	public RedisClientObject(int index) {
		super(index);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RedisTemplate initalRedisTemplate(RedisConnectionFactory con) {
		return SpringRedisTemplateUtil.getObjectRedisTemplate(con);
	}
	


}
