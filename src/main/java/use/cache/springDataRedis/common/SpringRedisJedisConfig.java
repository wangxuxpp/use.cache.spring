package use.cache.springDataRedis.common;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

import use.cache.springDataRedis.conf.RedisPoolParameter;

/**
 * redis连接配置对象
 * 项目名称:bss
 * 类型名称:SpringRedisConfig
 * 类型描述:
 * 作者:wx
 * 创建时间:2018年5月2日
 * @version:
 */
public class SpringRedisJedisConfig implements ISpringRedisJedisConfig{

	/**
	 * redis连接配置文件连接信息
	 */
	private RedisPoolParameter fsrs = null;
	
	public SpringRedisJedisConfig(RedisPoolParameter value)
	{
		fsrs = value;
	}
	/**
	 * 获取Redis连接池对象
	 * @return Redis连接池对象
	 */
    public JedisClientConfiguration getJedisClientConfiguration() 
    {  
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpccb = (  
                JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();  
        GenericObjectPoolConfig GenericObjectPoolConfig = new GenericObjectPoolConfig();  
        
        GenericObjectPoolConfig.setMaxTotal(fsrs.getMaxTotal());
        GenericObjectPoolConfig.setMaxIdle(fsrs.getMaxIdle());  
        GenericObjectPoolConfig.setMinIdle(fsrs.getMinIdle());
        GenericObjectPoolConfig.setMaxWaitMillis(fsrs.getMaxWaitMillis());  
        GenericObjectPoolConfig.setMinEvictableIdleTimeMillis(fsrs.getEvictableIdleTimeMillis());
        GenericObjectPoolConfig.setTestOnBorrow(fsrs.isTestOnBorrow());
        GenericObjectPoolConfig.setTestWhileIdle(fsrs.isTestWhileIdle());
        GenericObjectPoolConfig.setTestOnReturn(fsrs.isTestOnReturn());
        
        return jpccb.poolConfig(GenericObjectPoolConfig).build();

    }
    /**
     * 获取Redis连接对象
     * @return Redis连接对象
     */
	public RedisStandaloneConfiguration getRedisStandaloneConfiguration()
	{
		RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(fsrs.getIp(), fsrs.getPort());
		if(!fsrs.getPassword().equals(""))
		{
			rsc.setPassword(RedisPassword.of(fsrs.getPassword()));
		}
		return rsc;
	}
	/**
	 * 获取Redis连接对象
	 * @param dataBaseIndex 数据库缩影
	 * @return 获取Redis连接对象
	 */
	public RedisStandaloneConfiguration getRedisStandaloneConfiguration(int dataBaseIndex)
	{
		RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(fsrs.getIp(), fsrs.getPort());
		if(!fsrs.getPassword().equals(""))
		{
			rsc.setPassword(RedisPassword.of(fsrs.getPassword()));
		}
		rsc.setDatabase(dataBaseIndex);
		return rsc;
	}

}
