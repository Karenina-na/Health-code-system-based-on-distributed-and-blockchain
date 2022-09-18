package com.ybxt.nucleicacidserver.service;

import com.ybxt.nucleicacidserver.entity.NucleicAcidData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 查询核酸信息业务层
 */
public interface NucleicAcidService {

    /**
     * 根据ID号查询核酸信息
     * @param id    ID号
     * @return    核酸信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    NucleicAcidData GetNucleicAcidDataById(String id);

    /**
     * 根据personID号查询核酸信息
     * @param person_id    ID号
     * @return    核酸信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    List<NucleicAcidData> GetNucleicAcidDataByPersonID(String person_id);

    /**
     * 根据身份证号查询核酸信息
     * @param identity_id    ID号
     * @return    核酸信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    List<NucleicAcidData> GetNucleicAcidDataByIdentityID(String identity_id);
}
