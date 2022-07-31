package com.example.hello;

import java.time.LocalDateTime;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

public class Sample {

	public static void main(String[] args) {
		System.out.println("Hello World!!");

		LocalDateTime ldt = LocalDateTime.now();
		
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxTotal(1024);
		jpc.setMaxIdle(1024);
		jpc.setMinIdle(0);
		jpc.setBlockWhenExhausted(true);
		jpc.setMaxWaitMillis(5000);
		jpc.setMinEvictableIdleTimeMillis(30000);
		jpc.setTimeBetweenEvictionRunsMillis(5000);
		
		JedisConnectionFactory jcf = new JedisConnectionFactory(jpc);
		jcf.setHostName("127.0.0.1");
		jcf.setPort(6379);
		jcf.setTimeout(2000);
		jcf.setUsePool(true);
		jcf.afterPropertiesSet();
		
		StringRedisTemplate redisTemplate = new StringRedisTemplate(jcf);

		redisTemplate.opsForValue().set("key1", ldt.toString());
		String value = redisTemplate.opsForValue().get("key1");

		System.out.println(value);
		
		// デーモンではなく１回だけ実行して終了する場合はコネクションプールが動き続けて終わらないのでコネクションプールを終了
		jcf.destroy();
	}
}
