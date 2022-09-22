package com.ybxt.traceserver.controller;


import com.ybxt.traceserver.entity.MessageResult;
import com.ybxt.traceserver.entity.TraceData;
import com.ybxt.traceserver.service.ChangeService;
import com.ybxt.traceserver.service.TraceService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private ChangeService changeService;

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

    /**
     * 插入轨迹信息
     * @param TraceData    轨迹信息
     * @return  插入结果
     */
    @PostMapping("/trace")
    public MessageResult insertTraceData( @RequestBody TraceData TraceData) {
        MessageResult result=new MessageResult();
        boolean insertResult = changeService.insertTraceData(TraceData);
        if (insertResult) {
            result.SuccessMessageResult("插入成功");
            return result;
        }
        result.ErrorMessageResult("插入失败");
        return result;
    }

    /**
     * 根据轨迹信息id删除轨迹信息
     * @param id    轨迹信息id
     * @return  删除结果
     */
    @DeleteMapping("/trace/{id}")
    public MessageResult deleteTraceData(@PathVariable String id) {
        MessageResult result=new MessageResult();
        boolean deleteResult = changeService.deleteTraceData(id);
        if (deleteResult) {
            result.SuccessMessageResult("删除成功");
            return result;
        }
        result.ErrorMessageResult("删除失败");
        return result;
    }

    /**
     * 根据轨迹信息更新轨迹信息
     * @param TraceData    轨迹信息
     * @return  更新结果
     */
    @PutMapping("/trace")
    public MessageResult updateTraceData( @RequestBody TraceData TraceData) {
        MessageResult result=new MessageResult();
        boolean updateResult = changeService.updateTraceData(TraceData);
        if (updateResult) {
            result.SuccessMessageResult("更新成功");
            return result;
        }
        result.ErrorMessageResult("更新失败");
        return result;
    }
}
