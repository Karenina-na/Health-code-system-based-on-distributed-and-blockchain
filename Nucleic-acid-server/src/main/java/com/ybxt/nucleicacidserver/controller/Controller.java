package com.ybxt.nucleicacidserver.controller;

import com.ybxt.nucleicacidserver.entity.MessageResult;
import com.ybxt.nucleicacidserver.entity.NucleicAcidData;
import com.ybxt.nucleicacidserver.service.NucleicAcidService;
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
    private NucleicAcidService nucleicAcidService;

    /**
     * ID查询轨迹信息
     * @param id
     * @return
     */
    @GetMapping("/nucleicacid/id/{id}")
    public MessageResult getNucleicAcidById(@PathVariable String id) {
        MessageResult result=new MessageResult();
        NucleicAcidData nucleicAcidData = nucleicAcidService.GetNucleicAcidDataById(id);
        if (nucleicAcidData == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(nucleicAcidData);
        return result;
    }

    /**
     * personID查询轨迹信息
     * @param personID
     * @return
     */
    @GetMapping("/nucleicacid/personID/{personID}")
    public MessageResult getNucleicAcidByIdCard(@PathVariable String personID) {
        MessageResult result=new MessageResult();
        List<NucleicAcidData> nucleicAcidData = nucleicAcidService.GetNucleicAcidDataByPersonID(personID);
        if (nucleicAcidData == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(nucleicAcidData);
        return result;
    }

    /**
     * 身份证号查询轨迹信息
     * @param identity
     * @return
     */
    @GetMapping("/nucleicacid/identity/{identity}")
    public MessageResult getNucleicAcidByIdentity(@PathVariable String identity) {
        MessageResult result=new MessageResult();
        List<NucleicAcidData> nucleicAcidData = nucleicAcidService.GetNucleicAcidDataByIdentityID(identity);
        if (nucleicAcidData == null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(nucleicAcidData);
        return result;
    }
}
