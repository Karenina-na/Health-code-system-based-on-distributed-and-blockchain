package com.ybxt.traceserver.service;

import com.ybxt.traceserver.entity.TraceData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 查询轨迹业务层
 */
public interface TraceService {

    /**
     * 根据ID号查询轨迹信息
     * @param id    ID号
     * @return    轨迹信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    TraceData GetTraceDataById(String id);

    /**
     * 根据personID号查询轨迹信息
     * @param person_id    ID号
     * @return    轨迹信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    List<TraceData> GetTraceDataByPersonID(String person_id);

    /**
     * 根据身份证号查询轨迹信息
     * @param identity_id    ID号
     * @return    轨迹信息
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    List<TraceData> GetTraceDataByIdentityID(String identity_id);
}
