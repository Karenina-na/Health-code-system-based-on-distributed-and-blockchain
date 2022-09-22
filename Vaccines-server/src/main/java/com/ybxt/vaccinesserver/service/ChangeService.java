package com.ybxt.vaccinesserver.service;

import com.ybxt.vaccinesserver.entity.VaccinesData;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作疫苗数据
 */
public interface ChangeService {

    /**
     * 插入疫苗信息
     *
     * @param vaccinesData 疫苗信息
     * @return 插入结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean insertVaccinesData(VaccinesData vaccinesData);

    /**
     * 根据疫苗id删除疫苗信息
     *
     * @param id 疫苗信息id
     * @return 删除结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean deleteVaccinesData(String id);

    /**
     * 根据疫苗ID更新疫苗信息
     * @param vaccinesData 疫苗信息
     * @return  更新结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean updateVaccinesData(VaccinesData vaccinesData);
}
