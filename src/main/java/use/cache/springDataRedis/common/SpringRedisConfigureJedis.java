package use.cache.springDataRedis.common;

import use.cache.springDataRedis.conf.RedisPoolParameter;

public class SpringRedisConfigureJedis {

	private ISpringRedisJedisConfig springRedisConfig = null;
	
	public SpringRedisConfigureJedis(RedisPoolParameter param)
	{
		springRedisConfig = new SpringRedisJedisConfig(param);
	}

	public ISpringRedisJedisConfig getSpringRedisConfig() {
		return springRedisConfig;
	}
	
	
}
