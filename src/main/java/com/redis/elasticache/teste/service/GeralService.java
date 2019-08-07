package com.redis.elasticache.teste.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.redis.elasticache.teste.model.Usuario;

@Service
public class GeralService {

	@Autowired
	@Qualifier("redisTemplateImpl")
	private RedisTemplate<String, Object> redisTemplate;
	
	public String salvarUserOnRedis(String usuario, String senha) {
		
		redisTemplate.opsForValue().set(usuario, senha);
		redisTemplate.expire(usuario, 60, TimeUnit.SECONDS);
		
		return "User's key: "+usuario;
	}

	public String recuperarUsuarioOnRedis(String key) {
		return "Value for: " + key +" is: " +redisTemplate.opsForValue().get(key);
	}
	
	public void salvarUserOnRedisList(String key, Usuario usuario) {
		redisTemplate.opsForList().rightPush(key, usuario);
	}
	
	public List<Object> getAllUsersFromRedis(String key){
		return redisTemplate.opsForList().range(key, 0, -1);
	}
	
}
