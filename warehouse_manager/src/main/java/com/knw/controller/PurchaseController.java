package com.knw.controller;

import com.knw.entity.*;
import com.knw.page.Page;
import com.knw.service.InStoreService;
import com.knw.service.PurchaseService;
import com.knw.service.StoreService;
import com.knw.utils.TokenUtils;
import com.knw.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 01:15
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    StoreService storeService;
    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    InStoreService inStoreService;
    @PostMapping("/purchase-add")
    public Result purchaseAdd(@RequestBody Purchase purchase)
    {
        Result result = purchaseService.savePurchase(purchase);
        return  result;

    }
    @GetMapping("/store-list")
    public Result storeList()
    {
        List<Store> allStore = storeService.getAllStore();
        return Result.ok(allStore);
    }
    @GetMapping("/purchase-page-list")
    public Result purchasePageList(Purchase purchase, Page page)
    {
        Page purchasePage = purchaseService.queryPuchasePage(page, purchase);
        return Result.ok(purchasePage);

    }
    @DeleteMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable Integer buyId){
        //执行业务
        Result result = purchaseService.deletePurchase(buyId);
        //响应
        return result;
    }

    @PutMapping("/purchase-update")
    public Result updatePurchase(@RequestBody Purchase purchase){
        //执行业务
        Result result = purchaseService.updatePurchase(purchase);
        //响应
        return result;
    }
    @PostMapping("/in-warehouse-record-add")
    public Result inWareHouseRecordAdd(@RequestBody Purchase purchase,
                                       @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token)
    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();

        //创建InStore对象封装添加的入库单的信息
        InStore inStore = new InStore();
        inStore.setStoreId(purchase.getStoreId());
        inStore.setProductId(purchase.getProductId());
        inStore.setInNum(purchase.getFactBuyNum());
        inStore.setCreateBy(createBy);

        //执行业务
        Result result = inStoreService.saveInStore(inStore, purchase.getBuyId());

        //响应
        return result;

    }

}
