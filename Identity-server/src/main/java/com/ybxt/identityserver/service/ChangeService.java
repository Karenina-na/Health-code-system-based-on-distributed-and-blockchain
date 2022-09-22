package com.ybxt.identityserver.service;

import com.ybxt.identityserver.entity.PersonData;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作个人信息
 */
public interface ChangeService {

    /**
     * 插入个人信息
     *
     * @param personData 个人信息
     * @return 插入结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean insertPersonData(PersonData personData);

    /**
     * 根据个人信息id删除个人信息
     *
     * @param id 个人信息id
     * @return 删除结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean deletePersonData(String id);

    /**
     * 根据个人信息更新个人信息
     * @param personData
     * @return  更新结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean updatePersonData(PersonData personData);
}
