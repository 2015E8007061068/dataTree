package dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import model.MetadataModel;

public interface MetadataModelMapper {
	
	//根据主键来得到一条元数据记录
	MetadataModel selectByPrimaryKey(String filePath);
	//向数据库中插入一条记录
	int insert(MetadataModel record);
	//选择出所有元数据记录
	List<MetadataModel> selectAll();
	//根据日期选择出符合要求的元数据项集合
	List<MetadataModel> selectByDate(Date date);
	//根据地区选择出符合要求的元数据项集合
	List<MetadataModel> selectByRegion(String region);
	//根据卫星选择出符合要求的元数据集合
	List<MetadataModel> selectByStatellite(String statellite);
 	//根据卫星和传感器选择出符合要求的元数据项集合
	List<MetadataModel> selectByStatelliteAndSensor(@Param("statellite")String statellite,@Param("sensor")String sensor);
	//根据卫星、传感器、时间、地域选择出元数据集合
	List<MetadataModel> selectBySTSR(@Param("statellite")String statellite,@Param("sensor")String sensor,@Param("times")Date times,
			@Param("region")String region);
	//根据时间、地域选择出元数据集合
	List<MetadataModel> selectByTimeAndRegion(@Param("times")Date times,@Param("region")String region);
	//根据时间、卫星选择出元数据集合
	List<MetadataModel> selectByTimeAndStatellite(@Param("times")Date times,@Param("statellite")String statellite);
	//根据卫星、地域选择出元数据集合
	List<MetadataModel> selectByRegionAndStatellite(@Param("region")String region,@Param("statellite")String statellite);
	//根据时间、卫星、地域选择出元数据集合
	List<MetadataModel> selectByTSR(@Param("statellite")String statellite,@Param("region")String region,@Param("times")Date times);
	//根据时间、卫星、传感器选择出元数据集合
	List<MetadataModel> selectByTSS(@Param("statellite")String statellite,@Param("sensor")String sensor,@Param("times")Date times);
	//根据卫星、传感器、地域选择出元数据集合
	List<MetadataModel> selectByRSS(@Param("statellite")String statellite,@Param("sensor")String sensor,@Param("region")String region);
	
}
