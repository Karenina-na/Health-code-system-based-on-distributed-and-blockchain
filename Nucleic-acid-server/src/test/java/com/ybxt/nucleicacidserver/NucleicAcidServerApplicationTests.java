package com.ybxt.nucleicacidserver;

import com.ybxt.nucleicacidserver.service.NucleicAcidService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class NucleicAcidServerApplicationTests {

    @Resource
    NucleicAcidService nucleicAcidService;

    @Test
    void ApplicationTest() {
        System.out.println(nucleicAcidService.GetNucleicAcidDataById("1"));
        System.out.println(nucleicAcidService.GetNucleicAcidDataByPersonID("1"));
        System.out.println(nucleicAcidService.GetNucleicAcidDataByIdentityID("111111111111111111"));
    }
}
