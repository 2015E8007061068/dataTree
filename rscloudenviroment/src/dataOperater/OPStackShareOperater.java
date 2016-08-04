package dataOperater;

import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import dao.MetadataModelMapper;
import model.MetadataModel;
import utils.OperaterTools;
import virtualdirectory.RSVEStudioChild;

/**
 * @author XuDong
 * 2016-7-31
 * �Զ���swift��Ŀ���ļ��ƶ����½�Ŀ������Ŀ¼�еķ�����
 */
public class OPStackShareOperater {

	// swift������
	// private static RSVEStudioChild rSVeStudio ;
	// ���ݿ�������
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private final Logger logger = Logger.getLogger(Test.class);
	// ����������
	private  OperaterTools operaterTools;
	// ��ѯ�ֶ�
	private final String regionType = "region";
	private final String timeType = "times";
	private final String satelliteType = "satellite";
	private final String sensorType = "sensor";
	// �����ļ�Ԫ���ݸ�����������ļ���
	private static List<String> regions;
	private List<Date> times;
	private List<String> satellites;

	static SqlSession session;
	static MetadataModelMapper metadataModelMapper;

	@SuppressWarnings("unchecked")
	public OPStackShareOperater() {
		// rSVeStudio = new RSVEStudioChild();
		operaterTools = new OperaterTools();
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session = sqlSessionFactory.openSession();
		metadataModelMapper = session.getMapper(MetadataModelMapper.class);

		// �õ���ַ������
		List<MetadataModel> list = metadataModelMapper.selectAll();
		List<String> sources1 = new ArrayList<String>();
		List<String> sources2 = new ArrayList<String>();
		List<Date> sources3 = new ArrayList<Date>();

		for (int i = 0; i < list.size(); i++) {
			sources1.add(list.get(i).getRegion());
			sources2.add(list.get(i).getStatellite());
			sources3.add(list.get(i).getDate());
		}
		regions = operaterTools.removeDuplicateWithOrder(sources1);
		satellites = operaterTools.removeDuplicateWithOrder(sources2);
		times = operaterTools.removeDuplicateWithOrder(sources3);

		System.out.println("xxxxxxxxxxx----" + regions.get(1));

	}

	/*
	 * �Զ����ɵ���Ŀ¼�� ������copy������Щ��������openstack4j/�������������Ƿ����ļ����ӵ�API
	 */
	public void generateOneD(String name, String Type) {

		// ����openstack ���Ȩ��
		RSVEStudioChild rSVeStudio = new RSVEStudioChild();
		boolean er = rSVeStudio.validation("pipsCloud", "pipsCloudTeam");
		if (er == true) {
			System.out.println("=====����openstack�ɹ�====");
		} else {
			System.out.println("=====����openstackʧ��====");

		}

		// �������
		if (rSVeStudio.isContainerName(name)
				&& (Type.equals(regionType) || Type.equals(timeType) || Type.equals(satelliteType))) {
			System.out.println("�����ʽ��ȷ����������");
		} else {
			throw new IllegalArgumentException("������ʽ��������밴�˸�ʽ����<�û���������Ŀ¼��������> " + "Ŀ¼��������Ϊregion/times/satellite");
		}

		// �������
		if (Type.equals(regionType)) {

			for (int i = 0; i < regions.size(); i++) {

				// ������һ������Ŀ¼
				rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/" + regions.get(i));
				// ���洢�е��ļ�������Ŀ���ļ�����
				List<MetadataModel> list1 = metadataModelMapper.selectByRegion(regions.get(i));
				for (int j = 0; j < list1.size(); j++) {
					String filePath = list1.get(j).getFileUrl();
					System.out.println(filePath);

					String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
					System.out.println(filename);
					rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx" + j,
							"/home/" + regions.get(i));
				}
			}
		}

		// �����Ƿ�
		if (Type.equals(satelliteType)) {

			for (int i = 0; i < satellites.size(); i++) {
				// ������һ������Ŀ¼
				rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/" + satellites.get(i));
				// ���洢�е��ļ�������Ŀ���ļ�����
				List<MetadataModel> list2 = metadataModelMapper.selectByStatellite(satellites.get(i));
				for (int j = 0; j < list2.size(); j++) {
					String filePath = list2.get(j).getFileUrl();
					System.out.println(filePath);

					String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
					System.out.println(filename);
					rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx" + j,
							"/home/" + satellites.get(i));
				}
			}
		}

		// ��ʱ���
		if (Type.equals(timeType)) {

			for (int i = 0; i < times.size(); i++) {
				// ������һ������Ŀ¼
				rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/" + times.get(i).toString());
				// ���洢�е��ļ�������Ŀ���ļ�����
				List<MetadataModel> list3 = metadataModelMapper.selectByDate(times.get(i));
				for (int j = 0; j < list3.size(); j++) {
					String filePath = list3.get(j).getFileUrl();
					System.out.println(filePath);

					String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
					System.out.println(filename);
					rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx" + j,
							"/home/" + times.get(i).toString());
				}

			}

		}

	}

	/*
	 * �Զ�����˫��Ŀ¼
	 */
	public void generateTwoD(String name, String OneType, String TwoType) {
		// ����openstack ���Ȩ��
		RSVEStudioChild rSVeStudio = new RSVEStudioChild();
		boolean er = rSVeStudio.validation("pipsCloud", "pipsCloudTeam");
		if (er == true) {
			System.out.println("=====����openstack�ɹ�====");
		} else {
			System.out.println("=====����openstackʧ��====");

		}
		// �������
		if (rSVeStudio.isContainerName(name)
				&& (OneType.equals(regionType) || OneType.equals(timeType) || OneType.equals(satelliteType))
				&& (TwoType.equals(regionType) || TwoType.equals(timeType) || TwoType.equals(satelliteType)
						|| TwoType.equals(sensorType))
				&& !OneType.equals(TwoType)) {
			System.out.println("�����ʽ��ȷ����������");
		} else {
			throw new IllegalArgumentException("������ʽ��������밴�˸�ʽ����<�û���������һ��Ŀ¼�������ͣ�����Ŀ¼��������> " + 
		"һ��Ŀ¼��������Ϊregion/times/satellite������Ŀ¼��������Ϊregion/times/satellite/sensor�������߲����ظ�");
		}
		
		// times/satellite Ŀ¼
		if(OneType.equals(timeType)&&TwoType.equals(satelliteType)){
			
			for(int i=0; i<times.size();i++){
				//�Բ�ѯ�������ǽ��ȥ��
				List<MetadataModel> list1 =metadataModelMapper.selectByDate(times.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getStatellite());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//������������Ŀ¼
					rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+times.get(i).toString()+"/"+reslut.get(j));
					//���洢�е��ļ�copy��Ŀ���ļ�����
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByTimeAndStatellite(times.get(i), reslut.get(j));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + times.get(i).toString()+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + times.get(i).toString()+"/"+reslut.get(j));
					}
				}
			}
		}
		
		// times/region
		if(OneType.equals(timeType)&&TwoType.equals(regionType)){
			
			for(int i=0; i<times.size();i++){
				//�Բ�ѯ���ĵ�����ȥ��
				List<MetadataModel> list1 =metadataModelMapper.selectByDate(times.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getRegion());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//������������Ŀ¼
					rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+times.get(i).toString()+"/"+reslut.get(j));
					//���洢�е��ļ�copy��Ŀ���ļ�����
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByTimeAndRegion(times.get(i), reslut.get(j));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + times.get(i).toString()+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + times.get(i).toString()+"/"+reslut.get(j));
					}
				}
			}
		}
		
		// satellite/times
		if(OneType.equals(satelliteType)&&TwoType.equals(timeType)){
			
			for(int i=0; i<satellites.size();i++){
				//�Բ�ѯ����ʱ����ȥ��
				List<MetadataModel> list1 =metadataModelMapper.selectByStatellite(satellites.get(i));
				List<Date> sources = new ArrayList<Date>();
				List<Date> reslut = new ArrayList<Date>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getDate());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//������������Ŀ¼
					rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+satellites.get(i)+"/"+reslut.get(j));
					//���洢�е��ļ�copy��Ŀ���ļ�����
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByTimeAndStatellite(reslut.get(j),satellites.get(i));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + satellites.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + satellites.get(i)+"/"+reslut.get(j));
					}
				}
			}
		}
		
		// satellite/sensor ����Ŀ¼�ɹ�������δ�ɹ�
		if(OneType.equals(satelliteType)&&TwoType.equals(sensorType)){
			
			for(int i=0; i<satellites.size();i++){
				//�Բ�ѯ����ʱ����ȥ��
				List<MetadataModel> list1 =metadataModelMapper.selectByStatellite(satellites.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getSensor());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//������������Ŀ¼
					rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+satellites.get(i)+"/"+reslut.get(j));
					//���洢�е��ļ�copy��Ŀ���ļ�����
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByStatelliteAndSensor(satellites.get(i),reslut.get(j));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + satellites.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + satellites.get(i)+"/"+reslut.get(j));
					}
				}
			}
		}
		
		// satellite/region  copyδ�ɹ�
		if(OneType.equals(satelliteType)&&TwoType.equals(regionType)){
			
			for(int i=0; i<satellites.size();i++){
				//�Բ�ѯ����ʱ����ȥ��
				List<MetadataModel> list1 =metadataModelMapper.selectByStatellite(satellites.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getRegion());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//������������Ŀ¼
					rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+satellites.get(i)+"/"+reslut.get(j));
					//���洢�е��ļ�copy��Ŀ���ļ�����
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByRegionAndStatellite(reslut.get(j),satellites.get(i));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + satellites.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + satellites.get(i)+"/"+reslut.get(j));
					}
				}
			}
		}
		
		// region/times  copyδ�ɹ�
		if(OneType.equals(regionType)&&TwoType.equals(timeType)){
			
			for(int i=0; i<regions.size();i++){
				
				//�Բ�ѯ����ʱ����ȥ��
				List<MetadataModel> list1 =metadataModelMapper.selectByRegion(regions.get(i));
				List<Date> sources = new ArrayList<Date>();
				List<Date> reslut = new ArrayList<Date>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getDate());
					
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//������������Ŀ¼
					rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+regions.get(i)+"/"+reslut.get(j).toString());
					//���洢�е��ļ�copy��Ŀ���ļ�����
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByTimeAndRegion(reslut.get(j),regions.get(i));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + regions.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + regions.get(i)+"/"+reslut.get(j).toString());
					}
				}
			}
		}
		
		// region/satellites
		if(OneType.equals(regionType)&&TwoType.equals(satelliteType)){
			
			for(int i=0; i<regions.size();i++){
				
				//�Բ�ѯ����ʱ����ȥ��
				List<MetadataModel> list1 =metadataModelMapper.selectByRegion(regions.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getStatellite());
					
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//������������Ŀ¼
					rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+regions.get(i)+"/"+reslut.get(j).toString());
					//���洢�е��ļ�copy��Ŀ���ļ�����
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByRegionAndStatellite(regions.get(i),reslut.get(j));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // ȥ��·��ǰ���һ��/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + regions.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + regions.get(i)+"/"+reslut.get(j).toString());
					}
				}
			}
		}
		
		
	}


}
