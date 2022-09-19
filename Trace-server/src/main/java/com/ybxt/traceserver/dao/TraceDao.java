package com.ybxt.traceserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.traceserver.entity.TraceData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 轨迹查询
 */
@Mapper
public interface TraceDao extends BaseMapper<TraceData> {

    /**
     * 根据PersonData的id号查询
     * @param person_id PersonData的id号
     * @return  PersonData
     */
    @Select("select * from trace_data_tb where person_id = #{person_id}")
    @ResultMap("traceData")
    List<TraceData> queryTraceDataByPersonID(String person_id);

    /**
     * 根据ID号码查询
     * @param id    ID号码
     * @return    个人信息
     */
    @Select("SELECT * FROM trace_data_tb WHERE id=#{id}")
    @ResultMap("traceData")
    TraceData queryTraceDataByID(String id);
}
