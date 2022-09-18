package com.ybxt.nucleicacidserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 核酸检测数据实体类
 */
@Data
@TableName("nucleic_acid_data_tb")
public class NucleicAcidData {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer person_id;
    private String nucleic_acid_company;
    private String nucleic_acid_sample_time;
    private String nucleic_acid_sample_place;
    private String nucleic_acid_test_result_time;
    private String nucleic_acid_test_place;
    private String nucleic_acid_test_result;
    @TableField(exist = false)
    private PersonData personData;
}
