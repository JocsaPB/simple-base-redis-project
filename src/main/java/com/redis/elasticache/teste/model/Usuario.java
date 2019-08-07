package com.redis.elasticache.teste.model;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = -4001630014594514441L;
	
	private String key;
	private String value;
	
	public Usuario() {}
	
	public Usuario(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
}
