package com.itheima.utils.PhoneMessage;

import java.util.Random;

/**
 *	������:�����������
 *	RandomUtil
 *	@author houzhiyang
 *	@data 2017-7-12����9:26:43
 *	@version 1.0.0
 */
public class RandomUtil {
	public static String getRandom(){
		String randNum = new Random().nextInt(1000000)+"";
		if(randNum.length()!=6){
			return getRandom();
		}
		return randNum;
	}
}
