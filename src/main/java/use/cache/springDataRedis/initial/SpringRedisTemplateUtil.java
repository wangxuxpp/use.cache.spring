package use.cache.springDataRedis.initial;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SpringRedisTemplateUtil {

	
    
	public static RedisTemplate getStringRedisTemplate(RedisConnectionFactory jcf) 
    {  
		RedisTemplate redisTemplate = new RedisTemplate();  
        redisTemplate.setKeySerializer(new StringRedisSerializer());  
        redisTemplate.setValueSerializer(new StringRedisSerializer());  
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());  
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());  
        redisTemplate.setConnectionFactory(jcf);  
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        
        return redisTemplate;  
    }  

    public static RedisTemplate getObjectRedisTemplate(RedisConnectionFactory jcf) 
    {  
        RedisTemplate redisTemplate = new RedisTemplate();  
        redisTemplate.setKeySerializer(new StringRedisSerializer());  
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());  
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());  
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());  
        redisTemplate.setConnectionFactory(jcf);  
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;  
    } 
}
