package com.ybxt.identityserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.identityserver.entity.PersonData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * ID信息条件查询
 */
@Mapper
public interface IDDao extends BaseMapper<PersonData> {

    /**
     * 根据ID查询用户名称
     * @param ID
     * @return
     */
    @Select("SELECT person_data_tb.`name` FROM person_data_tb WHERE id=#{ID}")
    String selectNameByID(@Param("ID") String ID);

    /**
     * 根据ID查询用户性别
     * @param ID
     * @return
     */
    @Select("SELECT person_data_tb.`gender` FROM person_data_tb WHERE id=#{ID}")
    String selectGenderByID(@Param("ID") String ID);

    /**
     * 根据ID查询用户身份证
     * @param ID
     * @return
     */
    @Select("SELECT person_data_tb.`identity_number` FROM person_data_tb WHERE id=#{ID}")
    String selectPersonIDByID(@Param("ID") String ID);

    /**
     * 根据ID查询用户电话号
     * @param ID
     * @return
     */
    @Select("SELECT person_data_tb.`phone_number` FROM person_data_tb WHERE id=#{ID}")
    String selectPhoneNumberByID(@Param("ID") String ID);

    /**
     * 根据ID查询用户信息
     * @param ID
     * @return
     */
    @Select("SELECT * FROM person_data_tb WHERE id=#{ID}")
    @ResultMap("personDataMap")
    PersonData selectPersonDataById(@Param("ID")String ID);
}
