package com.knw.service.impl;

import com.alibaba.fastjson.JSON;
import com.knw.entity.ProductType;
import com.knw.entity.Result;
import com.knw.mapper.ProductTypeMapper;
import com.knw.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 09:28
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public List<ProductType> allProductTypeTree() {
        List<ProductType> allProductType = productTypeMapper.findAllProductType();
        List<ProductType> productTypes = allTypeToTypeTree(allProductType, 0);
        stringRedisTemplate.opsForValue().set("product-type:alltree:", JSON.toJSONString(productTypes));
        return productTypes;
    }

    @Override
    public Result checkTypeCode(String typeCode) {
        ProductType productType =new ProductType();
        productType.setTypeCode(typeCode);
        ProductType productTypeByCodeOrName = productTypeMapper.findProductTypeByCodeOrName(productType);
        return Result.ok(productTypeByCodeOrName==null);
    }

    private List<ProductType> allTypeToTypeTree(List<ProductType> allTypeList, Integer parentId){

        List<ProductType> typeList = new ArrayList<>();
        for (ProductType productType : allTypeList) {
            if(productType.getParentId().equals(parentId)){
                typeList.add(productType);
            }
        }

        for (ProductType productType : typeList) {
            List<ProductType> childTypeList = allTypeToTypeTree(allTypeList, productType.getTypeId());
            productType.setChildProductCategory(childTypeList);
        }

        return typeList;
    }
    public  Result addProductType(ProductType productType)
    {
        //校验名称是否已经存在
        String typeName = productType.getTypeName();
        ProductType checktype = new ProductType();
        checktype.setTypeName(typeName);
        ProductType productTypeByName = productTypeMapper.findProductTypeByCodeOrName(checktype);
        if(productTypeByName!=null)
        {
            return Result.err(Result.CODE_ERR_BUSINESS,"已经存在同分类商品！");
        }

        int i = productTypeMapper.insertProductType(productType);
        if(i>0)
        {
            return Result.ok("添加分类成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加分类失败！");
    }

    @Override
    public Result deleteProductType(Integer typeId) {
        int i = productTypeMapper.deleteProductType(typeId);
        if(i>0)
        {
            return Result.ok("删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败");
    }

    @Override
    public Result updateTypeById(ProductType productType) {
        //先判断修改后的名称是否存在
        String typeName = productType.getTypeName();
        ProductType checkType = new ProductType();
        checkType.setTypeName(typeName);
        ProductType productTypeByCodeOrName = productTypeMapper.findProductTypeByCodeOrName(checkType);
        if(productTypeByCodeOrName!=null)
        {
            return Result.err(Result.CODE_ERR_BUSINESS,"该分类名称已存在");
        }
        int i = productTypeMapper.updateTypeById(productType);
        if(i>0)
        {
            return Result.ok("分类修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"分类修改失败！");
    }
}
