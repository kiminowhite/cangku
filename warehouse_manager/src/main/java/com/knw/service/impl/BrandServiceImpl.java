package com.knw.service.impl;

import com.knw.entity.Brand;
import com.knw.mapper.BrandMapper;
import com.knw.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 08:51
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;
    @Override
    public List<Brand> getAllBrands() {
        return brandMapper.findAllBrands();
    }
}
