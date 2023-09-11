package com.knw.service.impl;

import com.knw.entity.Purchase;
import com.knw.entity.Result;
import com.knw.mapper.PurchaseMapper;
import com.knw.page.Page;
import com.knw.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 01:22
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {
    PurchaseMapper purchaseMapper;

    public PurchaseServiceImpl(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    @Override
    public Result savePurchase(Purchase purchase) {
        //补充字段
        purchase.setFactBuyNum(purchase.getBuyNum());
        int i = purchaseMapper.insertPurchase(purchase);
        if(i>0)
        {
            return Result.ok("采购单添加成功，正在努力采购");

        }
        return Result.err(Result.CODE_ERR_BUSINESS,"采购单添加失败");
    }

    @Override
    public Page queryPuchasePage(Page page, Purchase purchase) {
        int total = purchaseMapper.selectPurchaseCount(purchase);
        page.setTotalNum(total);
        List<Purchase> purchases = purchaseMapper.selectPurchasePage(page, purchase);
        page.setResultList(purchases);
        return page;
    }

    @Override
    public Result deletePurchase(Integer buyId) {
        int i = purchaseMapper.deletePurchaseById(buyId);
        if(i>0)
        {
            return Result.ok("删除成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败");
    }

    @Override
    public Result updatePurchase(Purchase purchase) {

        int i = purchaseMapper.updatePurchaseById(purchase);
        if(i>0)
        {
            return Result.ok("修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败");
    }
}
