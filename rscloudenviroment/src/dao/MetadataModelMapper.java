package dao;

import java.sql.Date;
import java.util.List;

import model.MetadataModel;

public interface MetadataModelMapper {
	
	//�����������õ�һ��Ԫ���ݼ�¼
	MetadataModel selectByPrimaryKey(String filePath);
	//�����ݿ��в���һ����¼
	int insert(MetadataModel record);
	//��������ѡ�������Ҫ���Ԫ�������
	List<MetadataModel> selectByDate(Date date);
	//���ݵ���ѡ�������Ҫ���Ԫ�������
	List<MetadataModel> selectByRegion(String region);
	//�������Ǻʹ�����ѡ�������Ҫ���Ԫ�������
	List<MetadataModel> selectByStatelliteAndSensor(String stateandsensor);

}
