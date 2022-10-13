package com.ybxt.nucleicacidserver.controller;

import com.ybxt.nucleicacidserver.entity.MessageResult;
import com.ybxt.nucleicacidserver.entity.NucleicAcidData;
import com.ybxt.nucleicacidserver.service.ChangeService;
import com.ybxt.nucleicacidserver.service.NucleicAcidService;
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
    private NucleicAcidService nucleicAcidService;

    @Resource
    private ChangeService changeService;

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


    /**
     * 插入核酸信息
     * @param nucleicAcidData    核酸信
     * @return  插入结果
     */
    @PostMapping("/nucleicacid")
    public MessageResult insertNucleicAcidData( @RequestBody NucleicAcidData nucleicAcidData) {
        MessageResult result=new MessageResult();
        boolean insertResult = changeService.insertNucleicAcidData(nucleicAcidData);
        if (insertResult) {
            result.SuccessMessageResult("插入成功");
            return result;
        }
        result.ErrorMessageResult("插入失败");
        return result;
    }

    /**
     * 根据核酸信息id删除核酸信息
     * @param id    核酸信息id
     * @return  删除结果
     */
    @DeleteMapping("/nucleicacid/{id}")
    public MessageResult deleteNucleicAcidData(@PathVariable String id) {
        MessageResult result=new MessageResult();
        boolean deleteResult = changeService.deleteNucleicAcidData(id);
        if (deleteResult) {
            result.SuccessMessageResult("删除成功");
            return result;
        }
        result.ErrorMessageResult("删除失败");
        return result;
    }

    /**
     * 根据核酸信息更新核酸信息
     * @param nucleicAcidData    核酸信息
     * @return  更新结果
     */
    @PutMapping("/nucleicacid")
    public MessageResult updateNucleicAcidData( @RequestBody NucleicAcidData nucleicAcidData) {
        MessageResult result=new MessageResult();
        boolean updateResult = changeService.updateNucleicAcidData(nucleicAcidData);
        if (updateResult) {
            result.SuccessMessageResult("更新成功");
            return result;
        }
        result.ErrorMessageResult("更新失败");
        return result;
    }
}
