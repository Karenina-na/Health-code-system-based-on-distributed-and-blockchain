package com.ybxt.traceserver.service;

import com.ybxt.traceserver.entity.TraceData;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作行程数据
 */
public interface ChangeService {

    /**
     * 插入行程信息
     *
     * @param traceData 行程信息
     * @return 插入结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean insertTraceData(TraceData traceData);

    /**
     * 根据行程id删除行程信息
     *
     * @param id 行程信息id
     * @return 删除结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean deleteTraceData(String id);

    /**
     * 根据行程ID更新行程信息
     * @param traceData 行程信息
     * @return  更新结果
     */
    @Transactional(timeout = 5,rollbackFor = Exception.class)
    @GlobalTransactional
    boolean updateTraceData(TraceData traceData);
}
