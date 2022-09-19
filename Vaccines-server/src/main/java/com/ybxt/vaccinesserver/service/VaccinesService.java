package com.ybxt.vaccinesserver.service;

import com.ybxt.vaccinesserver.entity.VaccinesData;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 查询疫苗业务层
 */
public interface VaccinesService {

    /**
     * 根据ID号查询疫苗信息
     * @param id    ID号
     * @return    疫苗信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    VaccinesData GetVaccinesDataById(String id);

    /**
     * 根据personID号查询疫苗信息
     * @param person_id    ID号
     * @return    疫苗信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    List<VaccinesData> GetVaccinesDataByPersonID(String person_id);

    /**
     * 根据身份证号查询疫苗信息
     * @param identity_id    ID号
     * @return    疫苗信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    List<VaccinesData> GetVaccinesDataByIdentityID(String identity_id);
}
