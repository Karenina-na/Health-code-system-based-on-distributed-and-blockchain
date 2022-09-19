package com.ybxt.identityserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.identityserver.entity.PersonData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * 身份证条件查询
 */
@Mapper
public interface IdentityDAO extends BaseMapper<PersonData> {

    /**
     * 根据身份证查询用户名称
     * @param personID
     * @return
     */
    @Select("SELECT person_data_tb.`name` FROM person_data_tb WHERE identity_number=#{personID}")
    String selectNameByPersonID(@Param("personID") String personID);

    /**
     * 根据身份证查询用户性别
     * @param personID
     * @return
     */
    @Select("SELECT person_data_tb.`gender` FROM person_data_tb WHERE identity_number=#{personID}")
    String selectGenderByPersonID(@Param("personID") String personID);

    /**
     * 根据身份证查询用户身份证
     * @param personID
     * @return
     */
    @Select("SELECT person_data_tb.`identity_number` FROM person_data_tb WHERE identity_number=#{personID}")
    String selectPersonIDByPersonID(@Param("personID") String personID);

    /**
     * 根据身份证查询用户电话号
     * @param personID
     * @return
     */
    @Select("SELECT person_data_tb.`phone_number` FROM person_data_tb WHERE identity_number=#{personID}")
    String selectPhoneNumberByPersonID(@Param("personID") String personID);

    /**
     * 根据身份证查询用户信息
     * @param personID
     * @return
     */
    @Select("SELECT * FROM person_data_tb WHERE identity_number=#{personID}")
    @ResultMap("personDataMap")
    PersonData selectPersonDataByPersonID(@Param("personID")String personID);
}
