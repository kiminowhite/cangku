package com.knw.service.impl;

import com.knw.entity.InStore;
import com.knw.entity.Result;
import com.knw.mapper.InStoreMapper;
import com.knw.mapper.ProductMapper;
import com.knw.mapper.PurchaseMapper;
import com.knw.page.Page;
import com.knw.service.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 04:04
 */
@Service
public class InStoreServiceImpl implements InStoreService {
    @Autowired
    InStoreMapper inStoreMapper;
    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public Result selectInStorePage(Page page, InStore inStore) {
        int totalNum = inStoreMapper.selectInStoreCount(inStore);
        page.setTotalNum(totalNum);
        List<InStore> inStores = inStoreMapper.selectInStorePage(page, inStore);
        page.setResultList(inStores);
        return Result.ok(page);
    }

    @Override
    @Transactional
    public Result saveInStore(InStore inStore, Integer buyId) {
        //添加入库单
        int i = inStoreMapper.insertInStore(inStore);
        if(i>0){
            //根据id将采购单状态改为已入库
            int j = purchaseMapper.updateIsInById(buyId);
            if(j>0){
                return Result.ok("入库单添加成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");

    }

    @Transactional//事务处理
    @Override
    public Result confirmInStore(InStore inStore) {

        //根据id将入库单状态改为已入库
        int i = inStoreMapper.updateIsInById(inStore.getInsId());
        if(i>0){
            //根据商品id增加商品库存
            int j = productMapper.addInventById(inStore.getProductId(), inStore.getInNum());
            if(j>0){
                return Result.ok("入库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库失败！");
    }
}
