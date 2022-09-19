package com.ybxt.vaccinesserver.controller;

import com.ybxt.vaccinesserver.entity.MessageResult;
import com.ybxt.vaccinesserver.entity.VaccinesData;
import com.ybxt.vaccinesserver.service.VaccinesService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
