package dataOperater;

import java.io.IOException;
import java.io.Reader;
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
/*
 * �Զ�����swift������Ŀ¼�ķ���
 */
public class OPStackShareOperater {
	
	//swift������
	//private static RSVEStudioChild rSVeStudio ;
	//���ݿ�������
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private final Logger logger = Logger.getLogger(Test.class);
	//����������
	private static OperaterTools operaterTools;
	//��ѯ�ֶ�
	private final String regionType= "region";
	private final String timeType= "times";
	private final String satelliteType= "satellite";
	//�����ļ�Ԫ���ݸ�����������ļ���
	private static List<String> regions ;
	private List<String> times;
	private List<String> satellites;
	
	static SqlSession session;
	static MetadataModelMapper metadataModelMapper;
	
	public OPStackShareOperater(){
		//rSVeStudio = new  RSVEStudioChild();
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
		
		//�õ���ַ������
		List<MetadataModel> list = metadataModelMapper.selectAll();
		List<String> sources1 = new ArrayList<String>();
		List<String> sources2 = new ArrayList<String>();
		List<String> sources3 = new ArrayList<String>();
		
		for(int i =0;i<list.size();i++){
			sources1.add(list.get(i).getRegion());
			sources2.add(list.get(i).getStatellite());
			//sources3.add(list.get(i).getDate());
		}
		 regions= operaterTools.removeDuplicateWithOrder(sources1);
		 satellites=operaterTools.removeDuplicateWithOrder(sources2);
		
		 System.out.println("xxxxxxxxxxx----"+regions.get(1));
		
	}
	
	
	
	
	//�Զ����ɵ���Ŀ¼
	public void generateOneD(String name,String Type){
		
		
		//����openstack ���Ȩ��
		RSVEStudioChild rSVeStudio = new  RSVEStudioChild();
		boolean er = rSVeStudio.validation("pipsCloud", "pipsCloudTeam");
		if (er == true) {
			System.out.println("=====����openstack�ɹ�====");
		} else {
			System.out.println("=====����openstackʧ��====");
			
		}
		
		//�������
		if(Type.equals(regionType)||Type.equals(timeType)||Type.equals(satelliteType)){
			System.out.println("�����ʽ��ȷ����������");
		}else{
			System.out.println("�����ʽ�д������������룡");
		}
		
		//�������
		if(Type.equals(regionType)){

			for(int i=0;i<regions.size();i++){
				
				//������һ������Ŀ¼
				rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+regions.get(i));
				//���洢�е��ļ�������Ŀ���ļ�����
				List<MetadataModel> list1=metadataModelMapper.selectByRegion(regions.get(i));
				for(int j =0;j<list1.size();j++){
					String filePath = list1.get(j).getFileUrl();
					System.out.println(filePath);
	
					String filename = filePath.split("\\/", 2)[1]; //ȥ��·��ǰ���һ��/
					System.out.println(filename);
					rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx"+j,"/home/"+regions.get(i));
				}
			}
		}
		//�����Ƿ�
		if(Type.equals(satelliteType)){
			
			for(int i=0;i<satellites.size();i++){
			//������һ������Ŀ¼
			rSVeStudio.createfilepath(name, "����Ŀ¼�ļ�", "/home/"+satellites.get(i));
			//���洢�е��ļ�������Ŀ���ļ�����
			List<MetadataModel> list2=metadataModelMapper.selectByStatellite(satellites.get(i));
			for(int j =0;j<list2.size();j++){
				String filePath = list2.get(j).getFileUrl();
				System.out.println(filePath);
				
				
				String filename = filePath.split("\\/", 2)[1]; //ȥ��·��ǰ���һ��/
				System.out.println(filename);
				rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx"+j,"/home/"+satellites.get(i));
			}
			}
		}
	}

}
