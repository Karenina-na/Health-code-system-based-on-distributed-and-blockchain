package com.ybxt.identityserver.service;

import com.ybxt.identityserver.entity.PersonData;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.transaction.annotation.Transactional;

/**
 * 根据ID查询个人信息
 */
public interface IDService {

    /**
     * 根据ID查询个人名字
     * @param id    个人信息ID
     * @return    名字
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    String GetName(String id);

    /**
     * 根据ID查询个人性别
     * @param id    个人信息ID
     * @return    性别
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    String GetGender(String id);

    /**
     * 根据ID查询个人身份证号
     * @param id    个人信息ID
     * @return    身份证号
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    String GetPersonID(String id);

    /**
     * 根据ID查询个人电话号码
     * @param id    个人信息ID
     * @return    电话号码
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    String GetPhone(String id);

    /**
     * 根据ID查询个人信息
     * @param person_id 身份证
     * @return  个人信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    PersonData GetPersonData(String person_id);
}
