package zm.village.service;

import java.util.List;

import zm.village.dao.Land;


/**
 * @author 伍伴
 * @Date 2018年7月21日
 * @Description 土地类服务层
 * @version 1.0
 */

public interface  LandService {
    
	
	/**
     * @param 土地ID
     * @return 删除行数
     */
    int delete(Land record);
    
    /**
     * @param 土地ID
     * @return 删除行数
     */
    int delete(Integer record);

    /**
     * @param 土地信息ID
     * @return 插入行的主键
     */
    int insert(Land record);
    
    
    /**
     * @return 土地信息列表
     */
    List<Land> selectAll();

    /**
     * @param 土地ID
     * @return 土地信息
     */
    Land select(Land record);
    
    /**
     * @param 土地Id
     * @return 土地信息
     */
    Land getById(Integer record);
    
    /**
     * @param 用户Id
     * @return 用户发布的土地
     */
    List<Land> getByuserId(Integer record);
    
    /**
     * @param 土地类型type
     * @return 土地信息
     */
    List<Land> getByType(Integer type);
    
    /**
     * @param 土地类型type
     * @return 土地信息
     */
    List<Land> getByTypePrice(Integer type);

    /**
     * @param 土地信息ID
     * @return 更新行数
     */
    int update(Land record);
    
    /**
     * @param  土地id数组
     * @return 删除的行数
     */
    int deleteMany(Integer[] record);
}
