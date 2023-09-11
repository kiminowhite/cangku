package com.knw.controller;

import com.knw.entity.CurrentUser;
import com.knw.entity.OutStore;
import com.knw.entity.Result;
import com.knw.entity.Store;
import com.knw.page.Page;
import com.knw.service.OutStoreService;
import com.knw.service.StoreService;
import com.knw.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-12 01:34
 */
@RestController
@RequestMapping("/outstore")
public class OutStoreController {
    @Autowired
    OutStoreService outStoreService;
    @Autowired
    TokenUtils tokenUtils;
    @PostMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore, @RequestHeader String token)

    {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        outStore.setCreateBy(userId);
        Result result = outStoreService.saveOutStore(outStore);
        return result;
    }

    @Autowired
    StoreService storeService;
    @GetMapping("/store-list")
    public Result storeList()
    {
        List<Store> allStore = storeService.getAllStore();
        return Result.ok(allStore);
    }
    @GetMapping("/outstore-page-list")
    public Result outStorePageList(Page page,OutStore outStore)
    {
        return outStoreService.outStorePage(page,outStore);
    }

    @PutMapping("/outstore-confirm")
    public  Result outStoreConfirm(@RequestBody OutStore outStore)
    {
        //执行业务
        Result result = outStoreService.confirmOutStore(outStore);
        //响应
        return result;
    }
}
