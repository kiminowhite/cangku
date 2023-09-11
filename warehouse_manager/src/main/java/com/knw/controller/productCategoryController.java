package com.knw.controller;

import com.knw.entity.ProductType;
import com.knw.entity.Result;
import com.knw.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 01:53
 */
@RestController
@RequestMapping("/productCategory")
public class productCategoryController {
    @Autowired
    ProductTypeService productTypeService;
    @GetMapping("/product-category-tree")
    public Result productCategoryTree()
    {
        List<ProductType> productTypes = productTypeService.allProductTypeTree();
        return Result.ok(productTypes);
    }
    @GetMapping("/verify-type-code")
    public Result verifyTypeCode(String typeCode)
    {
        Result result = productTypeService.checkTypeCode(typeCode);
        return result;
    }
    @PostMapping("/type-add")
            public Result typeAdd(@RequestBody ProductType productType)
    {
      return  productTypeService.addProductType(productType);
    }
    @DeleteMapping("/type-delete/{typeId}")
    public Result deleteTypeAndKids(@PathVariable("typeId") Integer typeId) //作为自己和父级 如果再有子集就需要递归细分了但是本项目不需要
    {
        Result result = productTypeService.deleteProductType(typeId);
        return  result;

    }
    @PutMapping("/type-update")
    public Result updateType(@RequestBody ProductType productType)
    {
     return productTypeService.updateTypeById(productType);
    }
}
