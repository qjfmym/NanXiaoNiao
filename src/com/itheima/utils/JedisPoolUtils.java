package com.itheima.utils;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {

	private static JedisPoolConfig config;
	private static JedisPool pool;
	
	static{
		// ��ȡproperties�����ļ�                                                                     //������׺��
		ResourceBundle bundle = ResourceBundle.getBundle("jedisConfig");
		int MaxTotal = Integer.parseInt(bundle.getString("MaxTotal"));
		config = new JedisPoolConfig();
		// �������������
		config.setMaxTotal(MaxTotal);
		// ��������������
		int MaxIdle = Integer.parseInt(bundle.getString("MaxIdle"));
		config.setMaxIdle(MaxIdle);
		// �������ӳض���
		String URL = bundle.getString("URL");
		int port = Integer.parseInt(bundle.getString("port"));
		pool = new JedisPool(config, URL,port);
	}
	
	// ��ȡjedis����
	public static Jedis getJedis(){
		return pool.getResource();
	}
	// �ر�jedis���ӵķ���
	public static void closeJedis(Jedis jedis){
		if(jedis!=null){
			jedis.close();
		}
	}
}
