package com.ybxt.traceserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.traceserver.entity.TraceData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * 操作行程信息
 */
@Mapper
public interface ChangeDataDao extends BaseMapper<TraceData> {

    /**
     * 添加行程信息
     * @param traceData   行程信息
     * @return  是否添加成功
     */
    @Insert("INSERT INTO trace_data_tb(id,person_id,province,city,street,time) " +
            "VALUES(#{id},#{person_id},#{province},#{city},#{street},#{time})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertTraceData(TraceData traceData);

    /**
     * 根据行程信息id删除行程信息
     *
     * @param id 行程信息id
     * @return 删除结果
     */
    @Delete("DELETE FROM trace_data_tb WHERE id=#{id}")
    int deleteTraceData(String id);

    /**
     * 根据行程信息更新行程信息
     * @param traceData 行程信息
     * @return  更新结果
     */
    int updateTraceData(TraceData traceData);
}
