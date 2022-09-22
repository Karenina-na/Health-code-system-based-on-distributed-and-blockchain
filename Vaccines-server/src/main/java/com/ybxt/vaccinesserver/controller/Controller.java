package com.ybxt.vaccinesserver.controller;

import com.ybxt.vaccinesserver.entity.MessageResult;
import com.ybxt.vaccinesserver.entity.VaccinesData;
import com.ybxt.vaccinesserver.service.ChangeService;
import com.ybxt.vaccinesserver.service.VaccinesService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询疫苗信息控制器
 */
@RestController
@RefreshScope
public class Controller {

    @Resource
    private VaccinesService vaccinesService;

    @Resource
    private ChangeService changeService;

    /**
     * ID查询疫苗信息
     * @param id
     * @return
     */
    @GetMapping("/vaccines/id/{id}")
    public MessageResult getVaccinesById(@PathVariable String id) {
        MessageResult result=new MessageResult();
        VaccinesData VaccinesData = vaccinesService.GetVaccinesDataById(id);
        if (VaccinesData == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(VaccinesData);
        return result;
    }

    /**
     * personID查询疫苗信息
     * @param personID
     * @return
     */
    @GetMapping("/vaccines/personID/{personID}")
    public MessageResult getVaccinesByIdCard(@PathVariable String personID) {
        MessageResult result=new MessageResult();
        List<VaccinesData> VaccinesData = vaccinesService.GetVaccinesDataByPersonID(personID);
        if (VaccinesData == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(VaccinesData);
        return result;
    }

    /**
     * 身份证号查询疫苗信息
     * @param identity
     * @return
     */
    @GetMapping("/vaccines/identity/{identity}")
    public MessageResult getVaccinesByIdentity(@PathVariable String identity) {
        MessageResult result=new MessageResult();
        List<VaccinesData> VaccinesData = vaccinesService.GetVaccinesDataByIdentityID(identity);
        if (VaccinesData == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(VaccinesData);
        return result;
    }

    /**
     * 插入疫苗信息
     * @param VaccinesData    疫苗信息
     * @return  插入结果
     */
    @PostMapping("/vaccines")
    public MessageResult insertVaccinesData( @RequestBody VaccinesData VaccinesData) {
        MessageResult result=new MessageResult();
        boolean insertResult = changeService.insertVaccinesData(VaccinesData);
        if (insertResult) {
            result.SuccessMessageResult("插入成功");
            return result;
        }
        result.ErrorMessageResult("插入失败");
        return result;
    }

    /**
     * 根据疫苗信息id删除疫苗信息
     * @param id    疫苗信息id
     * @return  删除结果
     */
    @DeleteMapping("/vaccines/{id}")
    public MessageResult deleteVaccinesData(@PathVariable String id) {
        MessageResult result=new MessageResult();
        boolean deleteResult = changeService.deleteVaccinesData(id);
        if (deleteResult) {
            result.SuccessMessageResult("删除成功");
            return result;
        }
        result.ErrorMessageResult("删除失败");
        return result;
    }

    /**
     * 根据疫苗信息更新疫苗信息
     * @param VaccinesData    疫苗信息
     * @return  更新结果
     */
    @PutMapping("/vaccines")
    public MessageResult updateVaccinesData( @RequestBody VaccinesData VaccinesData) {
        MessageResult result=new MessageResult();
        boolean updateResult = changeService.updateVaccinesData(VaccinesData);
        if (updateResult) {
            result.SuccessMessageResult("更新成功");
            return result;
        }
        result.ErrorMessageResult("更新失败");
        return result;
    }
}
