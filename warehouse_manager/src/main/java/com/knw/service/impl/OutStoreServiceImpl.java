package com.knw.service.impl;

import com.knw.entity.OutStore;
import com.knw.entity.Product;
import com.knw.entity.Result;
import com.knw.mapper.OutStoreMapper;
import com.knw.mapper.ProductMapper;
import com.knw.page.Page;
import com.knw.service.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 01:40
 */
@Service
public class OutStoreServiceImpl implements OutStoreService {
    @Autowired
    OutStoreMapper outStoreMapper;
    @Autowired
    ProductMapper productMapper;
    public Result saveOutStore(OutStore outStore)
    {
        int i = outStoreMapper.insertOutStore(outStore);
        if(i>0)
        {
           return Result.ok("添加出库单成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加出库单失败");
    }

    @Override
    public Result outStorePage(Page page, OutStore outStore) {
        int totalNum = outStoreMapper.outStoreCount(outStore);
        page.setTotalNum(totalNum);
        List<OutStore> outStores = outStoreMapper.outStorePage(page, outStore);
        page.setResultList(outStores);
        return Result.ok(page);
    }

    @Override
    public Result confirmOutStore(OutStore outStore) {

        //根据商品id查询商品,前端虽然有判断了，但是可能期间有变化，所以再次判断一下
        Product product = productMapper.selectProductById(outStore.getProductId());
        if(outStore.getOutNum()>product.getProductInvent()){
            return Result.err(Result.CODE_ERR_BUSINESS, "商品库存不足");
        }

        //根据id将出库单状态改为已出库
        int i = outStoreMapper.updateIsOutById(outStore.getOutsId());
        if(i>0){
            //根据商品id减商品库存
            int j = productMapper.addInventById(outStore.getProductId(), -outStore.getOutNum());
            if(j>0){
                return Result.ok("出库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "出库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "出库失败！");
    }
}
