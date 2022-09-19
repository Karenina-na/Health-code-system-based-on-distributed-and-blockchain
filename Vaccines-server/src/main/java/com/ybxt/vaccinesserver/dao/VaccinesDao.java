package com.ybxt.vaccinesserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.vaccinesserver.entity.VaccinesData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 疫苗查询
 */
@Mapper
public interface VaccinesDao extends BaseMapper<VaccinesData> {

    /**
     * 根据PersonData的id号查询
     * @param person_id PersonData的id号
     * @return  PersonData
     */
    @Select("select * from vaccines_data_tb where person_id = #{person_id}")
    @ResultMap("vaccinesData")
    List<VaccinesData> queryVaccinesDataByPersonID(String person_id);

    /**
     * 根据ID号码查询
     * @param id    ID号码
     * @return    个人信息
     */
    @Select("SELECT * FROM vaccines_data_tb WHERE id=#{id}")
    @ResultMap("vaccinesData")
    VaccinesData queryVaccinesDataByID(String id);
}
