package com.itheima.service;

import com.itheima.domain.ResultInfo;

public interface FavoriteService {

	ResultInfo findRouteByRid(int rid);

	ResultInfo isFavoriteByRid(int rid, int uid);

	ResultInfo addFavorite(int rid, int uid);

	

}
