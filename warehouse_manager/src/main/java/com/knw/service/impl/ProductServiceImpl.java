package com.knw.service.impl;

import com.knw.entity.Product;
import com.knw.entity.Result;
import com.knw.mapper.ProductMapper;
import com.knw.page.Page;
import com.knw.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 09:20
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Override
    public Page queryProductPage(Page page, Product product) {
        int total = productMapper.selectProductCount(product);
        List<Product> products = productMapper.selectProductPage(page, product);
        page.setTotalNum(total);
        page.setResultList(products);
        return page;
    }
    @Value("${file.access-path}")
    String saveFilePath;

    @Override
    public Result addProduct(Product product) {
        //添加前判断型号是否存在
        Product productByNum = productMapper.findProductByNum(product.getProductNum());
        if(productByNum!=null)
        {
            return Result.err(Result.CODE_ERR_BUSINESS,"已存在该型号的商品！");
        }
        String imgs = product.getImgs();
        imgs = saveFilePath+imgs;
        product.setImgs(imgs);
        int i = productMapper.insertProduct(product);
        if(i>0)
        {
            return Result.ok("添加商品成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加商品失败");
    }

    @Override
    public Result changeProductState(Product product) {
        int i = productMapper.updateStateById(product);
        if(i>0)
        {
            return  Result.ok("修改商品状态成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改商品状态失败");
    }

    @Override
    public Result deleteProductByIds(List<Integer> productIds) {
        int i = productMapper.RemoveProductByIds(productIds);
        if(i>0)
        {
            return Result.ok("删除商品成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除商品失败");
    }

    @Override
    public Result updateProductById(Product product) {
        //判断修改后的型号是否已经存在
        Product productByNum = productMapper.findProductByNum(product.getProductNum());
        if ((productByNum!=null&&!productByNum.getProductId().equals(product.getProductId())))
        {
            return  Result.err(Result.CODE_ERR_BUSINESS,"修改后的型号已经存在");
        }
        //判断是否更换图片
        String imgs = product.getImgs();
        boolean contains = imgs.contains(saveFilePath);
        if(contains == true)
        {
            //包含图片地址 说明没有修改图片，直接更改
           int i1= productMapper.updateProductById(product);
           if(i1>0)
           {
               return  Result.ok("修改商品成功");
           }
           return Result.err(Result.CODE_ERR_BUSINESS,"修改商品失败");
        }
        //不包含地址 说明修改图片，需要修改img添加前缀
        imgs=saveFilePath+imgs;
        product.setImgs(imgs);
        int i2 = productMapper.updateProductById(product);
        if(i2>0)
        {
            return  Result.ok("修改商品成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改商品失败");
    }
}
