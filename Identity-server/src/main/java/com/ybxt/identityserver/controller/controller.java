package com.ybxt.identityserver.controller;

import com.ybxt.identityserver.entity.MessageResult;
import com.ybxt.identityserver.entity.PersonData;
import com.ybxt.identityserver.service.ChangeService;
import com.ybxt.identityserver.service.IDService;
import com.ybxt.identityserver.service.IdentityService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 查询个人信息控制器
 */
@RestController
@RefreshScope
public class controller {

    @Resource
    private IDService idService;

    @Resource
    private IdentityService IdentityService;

    @Resource
    private ChangeService changeService;

    /**
     * ID查询个人信息
     * @param id    ID
     * @return    个人信息
     */
    @GetMapping("/identity/id/{id}")
    public MessageResult getPersonDataById(@PathVariable String id) {
        MessageResult result=new MessageResult();
        PersonData personData = idService.GetPersonData(id);
        if( personData==null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(personData);
        return result;
    }

    /**
     * 身份证查询个人信息
     * @param identity    身份证
     * @return    个人信息
     */
    @GetMapping("/identity/identity/{identity}")
    public MessageResult getPersonDataByIdCard(@PathVariable String identity) {
        MessageResult result=new MessageResult();
        PersonData personData = IdentityService.GetPersonData(identity);
        if (personData==null) {
            result.ErrorMessageResult("未查询到数据");
            return result;
        }
        result.SuccessMessageResult(personData);
        return result;
    }

    /**
     * 插入个人信息
     * @param personData    个人信息
     * @return  插入结果
     */
    @PostMapping("/identity")
    public MessageResult insertPersonData( @RequestBody PersonData personData) {
        MessageResult result=new MessageResult();
        boolean insertResult = changeService.insertPersonData(personData);
        if (insertResult) {
            result.SuccessMessageResult("插入成功");
            return result;
        }
        result.ErrorMessageResult("插入失败");
        return result;
    }

    /**
     * 根据个人信息id删除个人信息
     * @param id    个人信息id
     * @return  删除结果
     */
    @DeleteMapping("/identity/{id}")
    public MessageResult deletePersonData(@PathVariable String id) {
        MessageResult result=new MessageResult();
        boolean deleteResult = changeService.deletePersonData(id);
        if (deleteResult) {
            result.SuccessMessageResult("删除成功");
            return result;
        }
        result.ErrorMessageResult("删除失败");
        return result;
    }

    /**
     * 根据个人信息更新个人信息
     * @param personData    个人信息
     * @return  更新结果
     */
    @PutMapping("/identity")
    public MessageResult updatePersonData( @RequestBody PersonData personData) {
        MessageResult result=new MessageResult();
        boolean updateResult = changeService.updatePersonData(personData);
        if (updateResult) {
            result.SuccessMessageResult("更新成功");
            return result;
        }
        result.ErrorMessageResult("更新失败");
        return result;
    }
}
