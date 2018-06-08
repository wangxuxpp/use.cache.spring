package use.cache.springDataRedis.initial;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import use.cache.springDataRedis.common.SpringRedisConfigureJedis;
import use.cache.springDataRedis.common.SpringRedisConfigureLettuce;
import use.cache.springDataRedis.common.SpringRedisConnectFactoryInital;
import use.cache.springDataRedis.conf.RedisPoolParameter;

public class SpringRedisFactoryUtil 
{
	private volatile static RedisPoolParameter fcon = null;
	
	private static RedisConnectionFactory mem = null;
	private static RedisConnectionFactory mem1 = null;
	private static RedisConnectionFactory mem2 = null;

	public static void lettuceConnection()
	{
		SpringRedisConfigureLettuce srcl = new SpringRedisConfigureLettuce(getConfigure());
		mem = SpringRedisConnectFactoryInital.getLettuceConnectionFactory(srcl.getSpringRedisConfig());
		mem1 = SpringRedisConnectFactoryInital.getLettuceConnectionFactory(srcl.getSpringRedisConfig() , 1);
		mem2 = SpringRedisConnectFactoryInital.getLettuceConnectionFactory(srcl.getSpringRedisConfig() , 2);
	}
	public static void jedisConnection()
	{
		SpringRedisConfigureJedis srcj = new SpringRedisConfigureJedis(getConfigure());
		mem = SpringRedisConnectFactoryInital.getJedisConnectionFactory(srcj.getSpringRedisConfig());
		mem1 = SpringRedisConnectFactoryInital.getJedisConnectionFactory(srcj.getSpringRedisConfig() , 1);
		mem2 = SpringRedisConnectFactoryInital.getJedisConnectionFactory(srcj.getSpringRedisConfig() , 2);
	}

	public static RedisPoolParameter getConfigure()
	{
		if(fcon == null)
		{
			synchronized(SpringRedisFactoryUtil.class)
			{
				if(fcon == null)
				{
					fcon = new RedisPoolParameter();
					fcon.readParameter();
				}
			}
		}
		return fcon;
	}
	
	public static RedisConnectionFactory getMem() {
		return mem;
	}

	public static RedisConnectionFactory getMem1() {
		return mem1;
	}

	public static RedisConnectionFactory getMem2() {
		return mem2;
	}
	

	
	
}
