package com.ybxt.vaccinesserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 个人信息实体类
 */
@Data
@TableName("person_data_tb")
public class PersonData {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String name;
    private String gender;
    private String identity;
    private String phone;
}
