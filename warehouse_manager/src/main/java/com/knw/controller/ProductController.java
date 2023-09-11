package com.knw.controller;

import com.knw.entity.*;
import com.knw.page.Page;
import com.knw.service.*;
import com.knw.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 08:19
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    StoreService storeService;
    @Autowired
    SupplyService supplyService;
    @Autowired
    BrandService brandService;
    @Autowired
    UnitService unitService;
    @Autowired
    PlaceService placeService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductTypeService productTypeService;
    @Value("${file.upload-path}")
    String uploadpath;
    @Autowired
    TokenUtils tokenUtils;

    @GetMapping("/store-list")
    public Result storeList()
    {
        List<Store> allStore = storeService.getAllStore();
        return Result.ok(allStore);
    }

    @GetMapping("/supply-list")
    public Result supplyList()
    {
        List<Supply> allSupplys = supplyService.getAllSupplys();
        return Result.ok(allSupplys);

    }
    @GetMapping("/brand-list")
    public Result brandList()
    {
        List<Brand> allBrands = brandService.getAllBrands();
        return Result.ok(allBrands);
    }
    @GetMapping("/unit-list")
    public Result unitList()
    {
        List<Unit> allUnits = unitService.getAllUnits();
        return Result.ok(allUnits);
    }
    @GetMapping("/place-list")
    public  Result placeList()
    {
        List<Place> allPlaces = placeService.getAllPlaces();
        return Result.ok(allPlaces);
    }
    @GetMapping("/product-page-list")
        public Result productPageList(Page page, Product product)
    {
        page = productService.queryProductPage(page, product);
        //响应
        return Result.ok(page);
    }
    @RequestMapping("/category-tree")
    public Result categoryTree(){
        //执行业务
        List<ProductType> typeTreeList = productTypeService.allProductTypeTree();
        //响应
        return Result.ok(typeTreeList);
    }
    @CrossOrigin //要解决资源跨域请求，同时放行请求
    @RequestMapping("/img-upload")
    public Result uploadimg(MultipartFile file)
            //不是上传到静态img，而是上传到编译后的文件。但是内路径中都写classpath
    {
        try {
            //拿到上传目录
            File uploadDir = ResourceUtils.getFile(uploadpath);
            String absolutePath = uploadDir.getAbsolutePath();//前缀,磁盘路径
            String originalFilename = file.getOriginalFilename();
            String uploadFilePath=absolutePath+"/"+originalFilename;
            file.transferTo(new File(uploadFilePath));
        } catch (Exception e) {
          e.printStackTrace();
          return Result.err(Result.CODE_ERR_BUSINESS,"图片上传失败");
        }
        return Result.ok("图片上传成功");
    }
   @PostMapping("/product-add")
    public  Result addProduct(@RequestBody Product product,@RequestHeader String token)
    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        product.setCreateBy(currentUser.getUserId());

        Result result = productService.addProduct(product);
        return result;

    }
    @PutMapping("/state-change")
    public Result changeProductState(@RequestBody Product product)
    {
        Result result = productService.changeProductState(product);
        return  result;
    }
    @DeleteMapping("/product-delete/{productId}")
    public Result deleteProduct(@PathVariable("productId") Integer productId)
    {
        List<Integer> list = new ArrayList<>();
        list.add(productId);
        Result result = productService.deleteProductByIds(list);
        return result;
    }
    @DeleteMapping("/product-list-delete")
    public Result deleteProductByIds(@RequestBody List<Integer> list)
    {
        Result result = productService.deleteProductByIds(list);
        return  result;
    }
    @PutMapping("/product-update")
    public  Result updateProductById(@RequestBody Product product,@RequestHeader String token)
    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        product.setUpdateBy(currentUser.getUserId());
        Result result = productService.updateProductById(product);
        return result;
    }
}
