package com.knw.service;

import com.knw.entity.Product;
import com.knw.entity.Result;
import com.knw.page.Page;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 09:13
 */
public interface ProductService {
    public Page queryProductPage(Page page, Product product);

    public Result addProduct(Product product);

    public Result changeProductState(Product product);

    public Result deleteProductByIds(List<Integer> productIds);

    public Result updateProductById(Product product);
}
