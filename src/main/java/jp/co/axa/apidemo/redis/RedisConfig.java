package jp.co.axa.apidemo.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Profile("dev")
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

  public static final String REDIS_KEY_EMPLOYEE = "employees";

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisSerializer<Object> serializer = redisSerializer();
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(serializer);
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(serializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean
  public RedisSerializer<Object> redisSerializer() {
    Jackson2JsonRedisSerializer<Object> serializer =
        new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    serializer.setObjectMapper(objectMapper);
    return serializer;
  }

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheWriter redisCacheWriter =
        RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
    RedisCacheConfiguration redisCacheConfiguration =
        RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()))
            .entryTtl(Duration.ofMinutes(1));
    return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
  }
}
