package com.ybxt.identityserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.identityserver.entity.PersonData;
import org.apache.ibatis.annotations.*;

/**
 * 操作身份信息
 */
@Mapper
public interface ChangeDataDao extends BaseMapper<PersonData> {

    /**
     * 插入身份信息
     *
     * @param id             身份信息id
     * @param name           身份信息名称
     * @param gender         性别
     * @param identityNumber 身份证号
     * @param phoneNumber    手机号
     * @return 插入结果
     */
    @Insert("INSERT INTO person_data_tb(id,name,gender,identity_number,phone_number) " +
            "VALUES(#{id},#{name},#{gender},#{identity},#{phone})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertPersonData(PersonData personData);

    /**
     * 根据身份信息id删除身份信息
     *
     * @param id 身份信息id
     * @return 删除结果
     */
    @Delete("DELETE FROM person_data_tb WHERE id=#{id}")
    int deletePersonData(String id);

    /**
     * 根据身份信更新身份信息
     * @param personData
     * @return  更新结果
     */
    int updatePersonData(PersonData personData);
}
