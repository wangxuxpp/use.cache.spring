package use.cache.springDataRedis.common;

import use.cache.springDataRedis.conf.RedisPoolParameter;

public class SpringRedisConfigureLettuce {

	private  ISpringRedisLettuceConfig springRedisConfig = null;
	
	public SpringRedisConfigureLettuce(RedisPoolParameter param)
	{
		springRedisConfig = new SpringRedisLettuceConfig(param);
	}
	public  ISpringRedisLettuceConfig getSpringRedisConfig() {
		return springRedisConfig;
	}
	
	
}
