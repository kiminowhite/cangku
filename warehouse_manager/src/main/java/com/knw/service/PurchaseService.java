package com.knw.service;

import com.knw.entity.Purchase;
import com.knw.entity.Result;
import com.knw.entity.Role;
import com.knw.page.Page;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 01:22
 */
public interface PurchaseService {
    public Result savePurchase(Purchase purchase);

    public Page queryPuchasePage(Page page, Purchase purchase);

    Result deletePurchase(Integer buyId);

    Result updatePurchase(Purchase purchase);
}
