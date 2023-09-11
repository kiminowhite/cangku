package com.knw.controller;

import com.knw.entity.InStore;
import com.knw.entity.Result;
import com.knw.entity.Store;
import com.knw.page.Page;
import com.knw.service.InStoreService;
import com.knw.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 03:22
 */
@RestController
@RequestMapping("/instore")
public class InStoreController {
    @Autowired
    StoreService storeService;
    @Autowired
    InStoreService inStoreService;
    @GetMapping("/store-list")
    public Result storeList()
    {
        List<Store> allStore = storeService.getAllStore();
        return Result.ok(allStore);
    }

    @GetMapping("/instore-page-list")
    public Result inStorePageList(InStore inStore, Page page)

    {
       return  inStoreService.selectInStorePage(page,inStore);
    }
    @PutMapping("/instore-confirm")
    public Result inStoreConfirm(@RequestBody InStore inStore)
    {
        Result result = inStoreService.confirmInStore(inStore);
        return result;
    }
}
