package com.knw.service;

import com.knw.entity.InStore;
import com.knw.entity.Result;
import com.knw.page.Page;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 04:04
 */
public interface InStoreService {
    public Result selectInStorePage(Page page, InStore inStore);

    Result saveInStore(InStore inStore, Integer buyId);

    Result confirmInStore(InStore inStore);
}
