package com.ybxt.nucleicacidserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.nucleicacidserver.entity.NucleicAcidData;
import com.ybxt.nucleicacidserver.entity.PersonData;
import org.apache.ibatis.annotations.*;

/**
 * 操作核酸信息
 */
@Mapper
public interface ChangeDataDao  extends BaseMapper<NucleicAcidData> {

    /**
     * 添加核酸信息
     * @param nucleicAcidData   核酸信息
     * @return  是否添加成功
     */
    @Insert("INSERT INTO nucleic_acid_data_tb(id,person_id,nucleic_acid_company," +
            "nucleic_acid_sample_time,nucleic_acid_sample_place," +
            "nucleic_acid_test_result_time,nucleic_acid_test_place,nucleic_acid_test_result) " +
            "VALUES(#{id},#{person_id},#{nucleic_acid_company}," +
            "#{nucleic_acid_sample_time},#{nucleic_acid_sample_place}," +
            "#{nucleic_acid_test_result_time},#{nucleic_acid_test_place},#{nucleic_acid_test_result})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertNucleicAcidData(NucleicAcidData nucleicAcidData);

    /**
     * 根据核酸信息id删除核酸信息
     *
     * @param id 核酸信息id
     * @return 删除结果
     */
    @Delete("DELETE FROM nucleic_acid_data_tb WHERE id=#{id}")
    int deleteNucleicAcidData(String id);

    /**
     * 根据核酸信息更新核酸信息
     * @param nucleicAcidData
     * @return  更新结果
     */
    int updateNucleicAcidData(NucleicAcidData nucleicAcidData);
}
