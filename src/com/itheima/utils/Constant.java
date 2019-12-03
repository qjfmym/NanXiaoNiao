package com.itheima.utils;

public interface Constant {
	// 已上架
	int RFLAG_SHANGJIA = 1;
	// 为上架
	int RFLAG_WEISHANGJIA = 0;
	// 是主题旅游
	int ISTHEMETOUR_YES = 1;
	// 不是主题旅游
	int ISTHEMETOUR_NO = 0;
	// redis库中的分类json数据
	String CATELISTJSON = "CATELISTJSON";
	//用户未激活
	String USER_NOT_ACTIVE="N"; 
	//用户已激活
	String USER_YES_ACTIVE="Y";
	
	String STATUS_YES_ACTIVATED = "Y"; // 激活
	String STATUS_NOT_ACTIVATED = "N"; // 未激活
	
}
