package use.cache.springDataRedis.common;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

public interface ISpringRedisLettuceConfig {

	/**
	 * 获取Redis连接池对象
	 * @return Redis连接池对象
	 */
	LettuceClientConfiguration getLettuceClientConfiguration(); 
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
