package com.knw.service.impl;

import com.knw.entity.Statistics;
import com.knw.mapper.StatisticsMapper;
import com.knw.mapper.StatisticsMapper;
import com.knw.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    //注入StatisticsMapper
    @Autowired
    private StatisticsMapper statisticsMapper;

    //统计各个仓库商品库存数量的业务方法
    @Override
    public List<Statistics> statisticsStoreInvent() {
        return statisticsMapper.statisticsStoreInvent();
    }
}
