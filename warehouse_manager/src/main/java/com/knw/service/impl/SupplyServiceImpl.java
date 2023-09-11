package com.knw.service.impl;

import com.knw.entity.Supply;
import com.knw.mapper.AuthMapper;
import com.knw.mapper.SupplyMapper;
import com.knw.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 08:44
 */
@Service
public class SupplyServiceImpl implements SupplyService {
    @Autowired
    SupplyMapper supplyMapper;

    @Override
    public List<Supply> getAllSupplys() {
        return supplyMapper.findAllSupplys();
    }
}
