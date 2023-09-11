package com.knw;

import com.knw.entity.CurrentUser;
import com.knw.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WarehouseManagerApplicationTests {

@Autowired
TokenUtils tokenUtils;
    @Test
    void contextLoads() {
        CurrentUser currentUser = tokenUtils.getCurrentUser("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDTEFJTV9OQU1FX1VTRVJDT0RFIjoiYWRtaW4iLCJDTEFJTV9OQU1FX1VTRVJJRCI6MSwiQ0xBSU1fTkFNRV9VU0VSTkFNRSI6Iui2hee6p-euoeeQhuWRmCIsImV4cCI6MTY5NDIwMjYzOCwiaWF0IjoxNjk0MTk5MDM4fQ.dYQt6WdXzHbPiTtatypV2z2HUiu2lzC4uyVra-NOl_0");
        System.out.println(currentUser);
    }

}
