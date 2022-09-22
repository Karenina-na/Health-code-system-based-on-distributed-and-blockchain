package com.ybxt.vaccinesserver.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybxt.vaccinesserver.entity.VaccinesData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * 操作疫苗信息
 */
@Mapper
public interface ChangeDataDao extends BaseMapper<VaccinesData> {

    /**
     * 添加疫苗信息
     * @param vaccinesData   疫苗信息
     * @return  是否添加成功
     */
    @Insert("INSERT INTO vaccines_data_tb(id,person_id,vaccines_type,vaccines_company,vaccines_number,date,place) " +
            "VALUES(#{id},#{person_id},#{prvaccines_typeovince},#{vaccines_company},#{vaccines_number},#{date},#{place})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertVaccinesData(VaccinesData vaccinesData);

    /**
     * 根据疫苗信息id删除疫苗信息
     *
     * @param id 疫苗信息id
     * @return 删除结果
     */
    @Delete("DELETE FROM vaccines_data_tb WHERE id=#{id}")
    int deleteVaccinesData(String id);

    /**
     * 根据疫苗信息更新疫苗信息
     * @param vaccinesData 疫苗信息
     * @return  更新结果
     */
    int updateVaccinesData(VaccinesData vaccinesData);
}
