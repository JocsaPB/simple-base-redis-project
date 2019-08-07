package com.redis.elasticache.teste.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.elasticache.teste.model.Usuario;
import com.redis.elasticache.teste.service.GeralService;

@RestController
@RequestMapping("/")
public class GeralEndpoint {

	@Autowired
	private GeralService geralService;
	
	@GetMapping(value = "/{usuario}/{senha}/save")
	public String salvarUserOnRedis(@PathVariable String usuario, @PathVariable String senha) {
		return geralService.salvarUserOnRedis(usuario, senha);
	}

	@PostMapping(value = "/{key}")
	public String salvarUserOnRedis(@PathVariable String key, @RequestBody Usuario usuario) {
		geralService.salvarUserOnRedisList(key, usuario);
		return "Salvo! Recupere com a chave: " + key;
	}
	
	@GetMapping(value = "/{key}")
	public String recuperarUsuarioOnRedis(@PathVariable String key) {
		return geralService.recuperarUsuarioOnRedis(key);
	}
	
	@GetMapping(value = "/{key}/list")
	public List<Object> recuperarTodosUsuariosOnRedis(@PathVariable String key) {
		return geralService.getAllUsersFromRedis(key);
	}
	
}