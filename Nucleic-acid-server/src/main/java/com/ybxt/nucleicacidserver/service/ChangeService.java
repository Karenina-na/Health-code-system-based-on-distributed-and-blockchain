package com.ybxt.nucleicacidserver.service;

import com.ybxt.nucleicacidserver.entity.NucleicAcidData;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作核酸数据
 */
public interface ChangeService {

    /**
     * 插入核酸信息
     *
     * @param nucleicAcidData 个人信息
     * @return 插入结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean insertNucleicAcidData(NucleicAcidData nucleicAcidData);

    /**
     * 根据核酸id删除核酸信息
     *
     * @param id 个人信息id
     * @return 删除结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean deleteNucleicAcidData(String id);

    /**
     * 根据核酸ID更新核酸信息
     * @param nucleicAcidData
     * @return  更新结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean updateNucleicAcidData(NucleicAcidData nucleicAcidData);
}
