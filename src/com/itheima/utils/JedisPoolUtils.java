package com.itheima.utils;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {

	private static JedisPoolConfig config;
	private static JedisPool pool;
	
	static{
		// 读取properties配置文件                                                                     //不带后缀名
		ResourceBundle bundle = ResourceBundle.getBundle("jedisConfig");
		int MaxTotal = Integer.parseInt(bundle.getString("MaxTotal"));
		config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(MaxTotal);
		// 设置最大空闲练级
		int MaxIdle = Integer.parseInt(bundle.getString("MaxIdle"));
		config.setMaxIdle(MaxIdle);
		// 创建连接池对象
		String URL = bundle.getString("URL");
		int port = Integer.parseInt(bundle.getString("port"));
		pool = new JedisPool(config, URL,port);
	}
	
	// 获取jedis连接
	public static Jedis getJedis(){
		return pool.getResource();
	}
	// 关闭jedis连接的方法
	public static void closeJedis(Jedis jedis){
		if(jedis!=null){
			jedis.close();
		}
	}
}
