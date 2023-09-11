package com.knw.service;

import com.knw.entity.Statistics;

import java.util.List;

public interface StatisticsService {

    //统计各个仓库商品库存数量的业务方法
    public List<Statistics> statisticsStoreInvent();
}
