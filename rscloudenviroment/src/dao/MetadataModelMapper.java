package dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import model.MetadataModel;

public interface MetadataModelMapper {
	
	//�����������õ�һ��Ԫ���ݼ�¼
	MetadataModel selectByPrimaryKey(String filePath);
	//�����ݿ��в���һ����¼
	int insert(MetadataModel record);
	//ѡ�������Ԫ���ݼ�¼
	List<MetadataModel> selectAll();
	//��������ѡ�������Ҫ���Ԫ�������
	List<MetadataModel> selectByDate(Date date);
	//���ݵ���ѡ�������Ҫ���Ԫ�������
	List<MetadataModel> selectByRegion(String region);
	//��������ѡ�������Ҫ���Ԫ���ݼ���
	List<MetadataModel> selectByStatellite(String statellite);
 	//�������Ǻʹ�����ѡ�������Ҫ���Ԫ�������
	List<MetadataModel> selectByStatelliteAndSensor(@Param("statellite")String statellite,@Param("sensor")String sensor);
	//�������ǡ���������ʱ�䡢����ѡ���Ԫ���ݼ���
	List<MetadataModel> selectBySTSR(@Param("statellite")String statellite,@Param("sensor")String sensor,@Param("times")Date times,
			@Param("region")String region);
	//����ʱ�䡢����ѡ���Ԫ���ݼ���
	List<MetadataModel> selectByTimeAndRegion(@Param("times")Date times,@Param("region")String region);
	//����ʱ�䡢����ѡ���Ԫ���ݼ���
	List<MetadataModel> selectByTimeAndStatellite(@Param("times")Date times,@Param("statellite")String statellite);
	//�������ǡ�����ѡ���Ԫ���ݼ���
	List<MetadataModel> selectByRegionAndStatellite(@Param("region")String region,@Param("statellite")String statellite);
	//����ʱ�䡢���ǡ�����ѡ���Ԫ���ݼ���
	List<MetadataModel> selectByTSR(@Param("statellite")String statellite,@Param("region")String region,@Param("times")Date times);
	//����ʱ�䡢���ǡ�������ѡ���Ԫ���ݼ���
	List<MetadataModel> selectByTSS(@Param("statellite")String statellite,@Param("sensor")String sensor,@Param("times")Date times);
	//�������ǡ�������������ѡ���Ԫ���ݼ���
	List<MetadataModel> selectByRSS(@Param("statellite")String statellite,@Param("sensor")String sensor,@Param("region")String region);
	
}
