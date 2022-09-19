package com.ybxt.traceserver.controller;


import com.ybxt.traceserver.entity.MessageResult;
import com.ybxt.traceserver.entity.TraceData;
import com.ybxt.traceserver.service.TraceService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询轨迹信息控制器
 */
@RestController
@RefreshScope
public class Controller {

    @Resource
    private TraceService traceService;

    /**
     * ID查询轨迹信息
     * @param id
     * @return
     */
    @GetMapping("/trace/id/{id}")
    public MessageResult getNucleicAcidById(@PathVariable String id) {
        MessageResult result=new MessageResult();
        TraceData traceData = traceService.GetTraceDataById(id);
        if (traceData == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(traceData);
        return result;
    }

    /**
     * personID查询轨迹信息
     * @param personID
     * @return
     */
    @GetMapping("/trace/personID/{personID}")
    public MessageResult getNucleicAcidByIdCard(@PathVariable String personID) {
        MessageResult result=new MessageResult();
        List<TraceData> traceDataList = traceService.GetTraceDataByPersonID(personID);
        if (traceDataList == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(traceDataList);
        return result;
    }

    /**
     * 身份证号查询轨迹信息
     * @param identity
     * @return
     */
    @GetMapping("/trace/identity/{identity}")
    public MessageResult getNucleicAcidByIdentity(@PathVariable String identity) {
        MessageResult result=new MessageResult();
        List<TraceData> traceDataList = traceService.GetTraceDataByIdentityID(identity);
        if (traceDataList == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(traceDataList);
        return result;
    }
}
