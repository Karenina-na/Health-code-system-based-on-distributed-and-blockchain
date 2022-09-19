package com.ybxt.vaccinesserver;

import com.ybxt.vaccinesserver.service.VaccinesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class VaccinesServerApplicationTests {

    @Resource
    VaccinesService vaccinesService;

    @Test
    void ApplicationTest() {
        System.out.println(vaccinesService.GetVaccinesDataById("1"));
        System.out.println(vaccinesService.GetVaccinesDataByPersonID("1"));
        System.out.println(vaccinesService.GetVaccinesDataByIdentityID("111111111111111111"));
    }
}
