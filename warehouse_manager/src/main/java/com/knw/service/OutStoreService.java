package com.knw.service;

import com.knw.entity.OutStore;
import com.knw.entity.Result;
import com.knw.page.Page;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 01:40
 */
public interface OutStoreService {
    public Result saveOutStore(OutStore outStore);

    public Result outStorePage(Page page,OutStore outStore);

    Result confirmOutStore(OutStore outStore);
}
