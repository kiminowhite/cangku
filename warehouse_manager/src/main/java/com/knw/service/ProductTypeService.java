package com.knw.service;

import com.knw.entity.ProductType;
import com.knw.entity.Result;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 09:14
 */
public interface ProductTypeService {
    List<ProductType>  allProductTypeTree();

    public Result checkTypeCode(String typeCode);
    public  Result addProductType(ProductType productType);

    public Result deleteProductType(Integer typeId);

    public Result updateTypeById(ProductType productType);

}
