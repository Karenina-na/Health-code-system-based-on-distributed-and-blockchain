package com.ybxt.identityserver.controller;

import com.ybxt.identityserver.entity.MessageResult;
import com.ybxt.identityserver.entity.PersonData;
import com.ybxt.identityserver.service.IDService;
import com.ybxt.identityserver.service.IdentityService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
