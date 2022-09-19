package com.ybxt.traceserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 轨迹数据实体类
 */
@Data
@TableName("trace_data_tb")
public class TraceData {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer person_id;
    private String province;
    private String city;
    private String street;
    private String time;
    @TableField(exist = false)
    private PersonData personData;
}
