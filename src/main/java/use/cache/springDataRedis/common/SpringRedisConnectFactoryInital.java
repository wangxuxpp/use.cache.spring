package use.cache.springDataRedis.common;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * redis 连接工厂
 * 项目名称:bss
 * 类型名称:SpringRedisInital
 * 类型描述:
 * 作者:wx
 * 创建时间:2018年5月2日
 * @version:
 */
public class SpringRedisConnectFactoryInital {

	/**
	 * 获取jedis连接工厂 默认数据库0
	 * @param conf
	 * @return
	 */
	public static JedisConnectionFactory getJedisConnectionFactory(ISpringRedisJedisConfig conf)
	{
		JedisConnectionFactory r = new JedisConnectionFactory(conf.getRedisStandaloneConfiguration(), 
															  conf.getJedisClientConfiguration());
		return r;
	}
	/**
	 * 获取jedis连接工程
	 * @param conf
	 * @param databaseIndex 数据库索引值
	 * @return
	 */
	public static JedisConnectionFactory getJedisConnectionFactory(ISpringRedisJedisConfig conf, int databaseIndex)
	{
		JedisConnectionFactory r = new JedisConnectionFactory(conf.getRedisStandaloneConfiguration(databaseIndex), 
															  conf.getJedisClientConfiguration());
		return r;
	}
	
	public static LettuceConnectionFactory getLettuceConnectionFactory(ISpringRedisLettuceConfig conf)
	{
		LettuceConnectionFactory  r = new LettuceConnectionFactory (conf.getRedisStandaloneConfiguration(), 
															  conf.getLettuceClientConfiguration());
		r.afterPropertiesSet();
		return r;
	}
	public static LettuceConnectionFactory getLettuceConnectionFactory(ISpringRedisLettuceConfig conf, int databaseIndex)
	{
		LettuceConnectionFactory  r = new LettuceConnectionFactory (conf.getRedisStandaloneConfiguration(databaseIndex), 
															  conf.getLettuceClientConfiguration());
		r.afterPropertiesSet();
		return r;
	}

}
