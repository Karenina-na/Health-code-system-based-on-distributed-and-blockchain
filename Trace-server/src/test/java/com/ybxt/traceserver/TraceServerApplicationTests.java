package com.ybxt.traceserver;

import com.ybxt.traceserver.service.TraceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TraceServerApplicationTests {
    @Resource
    TraceService traceService;

    @Test
    void ApplicationTest() {
        System.out.println(traceService.GetTraceDataById("1"));
        System.out.println(traceService.GetTraceDataByPersonID("1"));
        System.out.println(traceService.GetTraceDataByIdentityID("111111111111111111"));
    }

}
