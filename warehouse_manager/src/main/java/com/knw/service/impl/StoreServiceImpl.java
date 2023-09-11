package com.knw.service.impl;

import com.knw.entity.Store;
import com.knw.mapper.StoreMapper;
import com.knw.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 08:37
 */
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreMapper storeMapper;
    @Override
    public List<Store> getAllStore() {
        return storeMapper.findAllStores();
    }
}
