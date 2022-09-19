package com.ybxt.vaccinesserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 疫苗数据实体类
 */
@Data
@TableName("vaccines_data_tb")
public class VaccinesData {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer person_id;
    private String vaccines_type;
    private String vaccines_company;
    private String vaccines_number;
    private String date;
    private String place;
    @TableField(exist = false)
    private PersonData personData;
}
