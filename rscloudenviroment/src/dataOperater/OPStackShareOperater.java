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
 * 自动生成swift中虚拟目录的方法
 */
public class OPStackShareOperater {
	
	//swift操作类
	//private static RSVEStudioChild rSVeStudio ;
	//数据库连接类
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private final Logger logger = Logger.getLogger(Test.class);
	//辅助工具类
	private static OperaterTools operaterTools;
	//查询字段
	private final String regionType= "region";
	private final String timeType= "times";
	private final String satelliteType= "satellite";
	//存有文件元数据各种属性种类的集合
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
		
		//得到地址的种类
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
	
	
	
	
	//自动生成单级目录
	public void generateOneD(String name,String Type){
		
		
		//连接openstack 获得权限
		RSVEStudioChild rSVeStudio = new  RSVEStudioChild();
		boolean er = rSVeStudio.validation("pipsCloud", "pipsCloudTeam");
		if (er == true) {
			System.out.println("=====进入openstack成功====");
		} else {
			System.out.println("=====进入openstack失败====");
			
		}
		
		//检查输入
		if(Type.equals(regionType)||Type.equals(timeType)||Type.equals(satelliteType)){
			System.out.println("输入格式正确，继续操作");
		}else{
			System.out.println("输入格式有错误，请重新输入！");
		}
		
		//按地域分
		if(Type.equals(regionType)){

			for(int i=0;i<regions.size();i++){
				
				//建立第一级虚拟目录
				rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+regions.get(i));
				//将存储中的文件拷贝到目标文件夹中
				List<MetadataModel> list1=metadataModelMapper.selectByRegion(regions.get(i));
				for(int j =0;j<list1.size();j++){
					String filePath = list1.get(j).getFileUrl();
					System.out.println(filePath);
	
					String filename = filePath.split("\\/", 2)[1]; //去掉路径前面的一个/
					System.out.println(filename);
					rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx"+j,"/home/"+regions.get(i));
				}
			}
		}
		//按卫星分
		if(Type.equals(satelliteType)){
			
			for(int i=0;i<satellites.size();i++){
			//建立第一级虚拟目录
			rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+satellites.get(i));
			//将存储中的文件拷贝到目标文件夹中
			List<MetadataModel> list2=metadataModelMapper.selectByStatellite(satellites.get(i));
			for(int j =0;j<list2.size();j++){
				String filePath = list2.get(j).getFileUrl();
				System.out.println(filePath);
				
				
				String filename = filePath.split("\\/", 2)[1]; //去掉路径前面的一个/
				System.out.println(filename);
				rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx"+j,"/home/"+satellites.get(i));
			}
			}
		}
	}

}
