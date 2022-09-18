package com.ybxt.nucleicacidserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.nucleicacidserver.entity.NucleicAcidData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 核酸查询
 */
@Mapper
public interface NucleicAcidDao extends BaseMapper<NucleicAcidData> {

    /**
     * 根据PersonData的id号查询
     * @param person_id PersonData的id号
     * @return  PersonData
     */
    @Select("select * from nucleic_acid_data_tb where person_id = #{person_id}")
    @ResultMap("nucleicAcidData")
    List<NucleicAcidData> queryNucleicAcidDataByPersonID(String person_id);

    /**
     * 根据ID号码查询
     * @param id    ID号码
     * @return    个人信息
     */
    @Select("SELECT * FROM nucleic_acid_data_tb WHERE id=#{id}")
    @ResultMap("nucleicAcidData")
    NucleicAcidData queryNucleicAcidDataByID(String id);
}
