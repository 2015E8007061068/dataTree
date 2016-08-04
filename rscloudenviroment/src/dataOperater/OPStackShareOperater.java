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
 * 自动将swift中目标文件移动到新建目的虚拟目录中的方法类
 */
public class OPStackShareOperater {

	// swift操作类
	// private static RSVEStudioChild rSVeStudio ;
	// 数据库连接类
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private final Logger logger = Logger.getLogger(Test.class);
	// 辅助工具类
	private  OperaterTools operaterTools;
	// 查询字段
	private final String regionType = "region";
	private final String timeType = "times";
	private final String satelliteType = "satellite";
	private final String sensorType = "sensor";
	// 存有文件元数据各种属性种类的集合
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

		// 得到地址的种类
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
	 * 自动生成单级目录， 但现在copy函数有些错误，找找openstack4j/其它工具里面是否有文件链接的API
	 */
	public void generateOneD(String name, String Type) {

		// 连接openstack 获得权限
		RSVEStudioChild rSVeStudio = new RSVEStudioChild();
		boolean er = rSVeStudio.validation("pipsCloud", "pipsCloudTeam");
		if (er == true) {
			System.out.println("=====进入openstack成功====");
		} else {
			System.out.println("=====进入openstack失败====");

		}

		// 检查输入
		if (rSVeStudio.isContainerName(name)
				&& (Type.equals(regionType) || Type.equals(timeType) || Type.equals(satelliteType))) {
			System.out.println("输入格式正确，继续操作");
		} else {
			throw new IllegalArgumentException("参数格式输入错误！请按此格式输入<用户容器名，目录生成类型> " + "目录生成类型为region/times/satellite");
		}

		// 按地域分
		if (Type.equals(regionType)) {

			for (int i = 0; i < regions.size(); i++) {

				// 建立第一级虚拟目录
				rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/" + regions.get(i));
				// 将存储中的文件拷贝到目标文件夹中
				List<MetadataModel> list1 = metadataModelMapper.selectByRegion(regions.get(i));
				for (int j = 0; j < list1.size(); j++) {
					String filePath = list1.get(j).getFileUrl();
					System.out.println(filePath);

					String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
					System.out.println(filename);
					rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx" + j,
							"/home/" + regions.get(i));
				}
			}
		}

		// 按卫星分
		if (Type.equals(satelliteType)) {

			for (int i = 0; i < satellites.size(); i++) {
				// 建立第一级虚拟目录
				rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/" + satellites.get(i));
				// 将存储中的文件拷贝到目标文件夹中
				List<MetadataModel> list2 = metadataModelMapper.selectByStatellite(satellites.get(i));
				for (int j = 0; j < list2.size(); j++) {
					String filePath = list2.get(j).getFileUrl();
					System.out.println(filePath);

					String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
					System.out.println(filename);
					rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx" + j,
							"/home/" + satellites.get(i));
				}
			}
		}

		// 按时间分
		if (Type.equals(timeType)) {

			for (int i = 0; i < times.size(); i++) {
				// 建立第一级虚拟目录
				rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/" + times.get(i).toString());
				// 将存储中的文件拷贝到目标文件夹中
				List<MetadataModel> list3 = metadataModelMapper.selectByDate(times.get(i));
				for (int j = 0; j < list3.size(); j++) {
					String filePath = list3.get(j).getFileUrl();
					System.out.println(filePath);

					String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
					System.out.println(filename);
					rSVeStudio.copyObjectDuringContainers("home", filePath.split("\\/", 2)[1], name, "xxx" + j,
							"/home/" + times.get(i).toString());
				}

			}

		}

	}

	/*
	 * 自动生成双层目录
	 */
	public void generateTwoD(String name, String OneType, String TwoType) {
		// 连接openstack 获得权限
		RSVEStudioChild rSVeStudio = new RSVEStudioChild();
		boolean er = rSVeStudio.validation("pipsCloud", "pipsCloudTeam");
		if (er == true) {
			System.out.println("=====进入openstack成功====");
		} else {
			System.out.println("=====进入openstack失败====");

		}
		// 检查输入
		if (rSVeStudio.isContainerName(name)
				&& (OneType.equals(regionType) || OneType.equals(timeType) || OneType.equals(satelliteType))
				&& (TwoType.equals(regionType) || TwoType.equals(timeType) || TwoType.equals(satelliteType)
						|| TwoType.equals(sensorType))
				&& !OneType.equals(TwoType)) {
			System.out.println("输入格式正确，继续操作");
		} else {
			throw new IllegalArgumentException("参数格式输入错误！请按此格式输入<用户容器名，一级目录生成类型，二级目录生成类型> " + 
		"一级目录生成类型为region/times/satellite，二级目录生成类型为region/times/satellite/sensor，且两者不能重复");
		}
		
		// times/satellite 目录
		if(OneType.equals(timeType)&&TwoType.equals(satelliteType)){
			
			for(int i=0; i<times.size();i++){
				//对查询到的卫星结果去重
				List<MetadataModel> list1 =metadataModelMapper.selectByDate(times.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getStatellite());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//建立两级虚拟目录
					rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+times.get(i).toString()+"/"+reslut.get(j));
					//将存储中的文件copy到目标文件夹中
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByTimeAndStatellite(times.get(i), reslut.get(j));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
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
				//对查询到的地域结果去重
				List<MetadataModel> list1 =metadataModelMapper.selectByDate(times.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getRegion());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//建立两级虚拟目录
					rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+times.get(i).toString()+"/"+reslut.get(j));
					//将存储中的文件copy到目标文件夹中
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByTimeAndRegion(times.get(i), reslut.get(j));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
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
				//对查询到的时间结果去重
				List<MetadataModel> list1 =metadataModelMapper.selectByStatellite(satellites.get(i));
				List<Date> sources = new ArrayList<Date>();
				List<Date> reslut = new ArrayList<Date>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getDate());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//建立两级虚拟目录
					rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+satellites.get(i)+"/"+reslut.get(j));
					//将存储中的文件copy到目标文件夹中
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByTimeAndStatellite(reslut.get(j),satellites.get(i));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + satellites.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + satellites.get(i)+"/"+reslut.get(j));
					}
				}
			}
		}
		
		// satellite/sensor 分配目录成功，拷贝未成功
		if(OneType.equals(satelliteType)&&TwoType.equals(sensorType)){
			
			for(int i=0; i<satellites.size();i++){
				//对查询到的时间结果去重
				List<MetadataModel> list1 =metadataModelMapper.selectByStatellite(satellites.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getSensor());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//建立两级虚拟目录
					rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+satellites.get(i)+"/"+reslut.get(j));
					//将存储中的文件copy到目标文件夹中
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByStatelliteAndSensor(satellites.get(i),reslut.get(j));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + satellites.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + satellites.get(i)+"/"+reslut.get(j));
					}
				}
			}
		}
		
		// satellite/region  copy未成功
		if(OneType.equals(satelliteType)&&TwoType.equals(regionType)){
			
			for(int i=0; i<satellites.size();i++){
				//对查询到的时间结果去重
				List<MetadataModel> list1 =metadataModelMapper.selectByStatellite(satellites.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getRegion());
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//建立两级虚拟目录
					rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+satellites.get(i)+"/"+reslut.get(j));
					//将存储中的文件copy到目标文件夹中
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByRegionAndStatellite(reslut.get(j),satellites.get(i));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + satellites.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + satellites.get(i)+"/"+reslut.get(j));
					}
				}
			}
		}
		
		// region/times  copy未成功
		if(OneType.equals(regionType)&&TwoType.equals(timeType)){
			
			for(int i=0; i<regions.size();i++){
				
				//对查询到的时间结果去重
				List<MetadataModel> list1 =metadataModelMapper.selectByRegion(regions.get(i));
				List<Date> sources = new ArrayList<Date>();
				List<Date> reslut = new ArrayList<Date>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getDate());
					
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//建立两级虚拟目录
					rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+regions.get(i)+"/"+reslut.get(j).toString());
					//将存储中的文件copy到目标文件夹中
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByTimeAndRegion(reslut.get(j),regions.get(i));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
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
				
				//对查询到的时间结果去重
				List<MetadataModel> list1 =metadataModelMapper.selectByRegion(regions.get(i));
				List<String> sources = new ArrayList<String>();
				List<String> reslut = new ArrayList<String>();
				for(int k=0;k<list1.size();k++){
					sources.add(list1.get(k).getStatellite());
					
				}
				reslut = operaterTools.removeDuplicateWithOrder(sources);
				for(int j=0;j<reslut.size();j++){
					//建立两级虚拟目录
					rSVeStudio.createfilepath(name, "虚拟目录文件", "/home/"+regions.get(i)+"/"+reslut.get(j).toString());
					//将存储中的文件copy到目标文件夹中
					
					List<MetadataModel> list = 
							metadataModelMapper.selectByRegionAndStatellite(regions.get(i),reslut.get(j));
					for(int q=0;q<list.size();q++){
						String filePath = list.get(q).getFileUrl();
						System.out.println(filePath);

						String filename = filePath.split("\\/", 2)[1]; // 去掉路径前面的一个/
						System.out.println(filename+"==="+name+"===="+"xxx" + q+"===="+"/home/" + regions.get(i)+"/"+reslut.get(j));
						rSVeStudio.copyObjectDuringContainers("home", filename, name, "xxx" + q,
								"/home/" + regions.get(i)+"/"+reslut.get(j).toString());
					}
				}
			}
		}
		
		
	}


}
