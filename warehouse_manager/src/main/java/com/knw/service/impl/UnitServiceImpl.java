package com.knw.service.impl;

import com.knw.entity.Unit;
import com.knw.mapper.UnitMapper;
import com.knw.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 08:55
 */
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    UnitMapper unitMapper;
    @Override
    public List<Unit> getAllUnits() {
        return unitMapper.findAllUnits();
    }
}
