package dao;

import java.sql.Date;
import java.util.List;

import model.MetadataModel;

public interface MetadataModelMapper {
	
	//根据主键来得到一条元数据记录
	MetadataModel selectByPrimaryKey(String filePath);
	//向数据库中插入一条记录
	int insert(MetadataModel record);
	//根据日期选择出符合要求的元数据项集合
	List<MetadataModel> selectByDate(Date date);
	//根据地区选择出符合要求的元数据项集合
	List<MetadataModel> selectByRegion(String region);
	//根据卫星和传感器选择出符合要求的元数据项集合
	List<MetadataModel> selectByStatelliteAndSensor(String stateandsensor);

}
