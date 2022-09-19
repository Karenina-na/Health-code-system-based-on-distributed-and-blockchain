package com.ybxt.vaccinesserver.client;


import com.ybxt.vaccinesserver.client.fallback.PersonDataClientFallback;
import com.ybxt.vaccinesserver.entity.MessageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="gateway",fallbackFactory = PersonDataClientFallback.class)
public interface PersonDataClient {

    /**
     * ID查询个人信息
     * @param id    ID
     * @return    个人信息
     */
    @GetMapping("/identity/id/{id}")
    MessageResult getPersonDataById(@PathVariable("id") String id);

    /**
     * 身份证查询个人信息
     * @param identity    身份证
     * @return    个人信息
     */
    @GetMapping("/identity/identity/{identity}")
    MessageResult getPersonDataByIdCard(@PathVariable("identity") String identity);
}
