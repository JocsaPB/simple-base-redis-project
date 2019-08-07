package com.redis.elasticache.teste.config;

import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfigTest {
 
    @Value("${redis.host}")
    private String redisHostName;
 
    @Value("${redis.port}")
    private int redisPort;
 
    @Value("${redis.prefix}")
    private String redisPrefix;
 
    @Value("${redis.password}")
    private String redisPswd;
    
    @Value("${redis.jedis.pool.max.idle}")
    private int maxIdle;
 
    @Value("${redis.jedis.pool.min.idle}")
    private int minIdle;
    
    @Value("${redis.jedis.pool.max.total}")
    private int maxTotal;
    
    @Value("${redis.password}")
    private String password;
    
    @Bean
    public JedisConnectionFactory getJedisFactory() {
    	
    	RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHostName, redisPort);
    	
    	if(password != null && !password.isEmpty()) {
    		RedisPassword rp = RedisPassword.of(password);
    		
    		config.setPassword(rp);
    	}
    	
        return new JedisConnectionFactory(config, getJedisConfigurationImpl());
    }
    
    @Bean
    public JedisClientConfiguration getJedisConfigurationImpl() {
    	JedisClientConfiguration.JedisClientConfigurationBuilder jcc = JedisClientConfiguration.builder();
    	
    	GenericObjectPoolConfig genericPool = new GenericObjectPoolConfig();
    	genericPool.setMaxTotal(maxTotal);
    	genericPool.setMaxIdle(maxIdle);
    	genericPool.setMinIdle(minIdle);
    	
    	jcc.usePooling().poolConfig(genericPool);
    	return jcc.build();
    }
    
    @Bean(name="redisTemplateImpl")
    public RedisTemplate<String, Object> getRedisTemplateImpl() {
    	
    	RedisTemplate<String, Object> stringRedisTemplate = new RedisTemplate<>();
		stringRedisTemplate.setConnectionFactory(getJedisFactory());
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		
    	return stringRedisTemplate;
    }	
    
//	@Bean(name = "cacheManager") // Default cache manager is infinite
//	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//		return RedisCacheManager.builder(redisConnectionFactory)
//				.cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().prefixKeysWith(redisPrefix)).build();
//	}

	@Primary
	@Bean(name = "cacheManager12Hour")
	public CacheManager cacheManager1Hour(RedisConnectionFactory redisConnectionFactory) {
		Duration expiration = Duration.ofHours(12);
		return RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(
						RedisCacheConfiguration.defaultCacheConfig().prefixKeysWith(redisPrefix).entryTtl(expiration))
				.build();
	}

}