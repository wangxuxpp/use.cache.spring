package use.cache.springDataRedis.common;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

public interface ISpringRedisJedisConfig {

	/**
	 * 获取Redis连接池对象
	 * @return Redis连接池对象
	 */
	JedisClientConfiguration getJedisClientConfiguration(); 
	
	/**
	 * 获取Redis连接对象
	 * @param dataBaseIndex 数据库缩影
	 * @return 获取Redis连接对象
	 */
	RedisStandaloneConfiguration getRedisStandaloneConfiguration(int dataBaseIndex);
    /**
     * 获取Redis连接对象
     * @return Redis连接对象
     */
	RedisStandaloneConfiguration getRedisStandaloneConfiguration();
}
