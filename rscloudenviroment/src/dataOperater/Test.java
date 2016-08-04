package dataOperater;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.log4j.Logger;

import dao.MetadataModelMapper;
import model.MetadataModel;

public class Test {
	
	
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private final Logger logger = Logger.getLogger(Test.class);
	
	static{
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
				
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		String filePath = "/home/HJ1/sensor1/beijing/20150707/faaa";
		Date times = new Date(115, 6, 7);
		String region = "beijing";
		String statellite = "HJ1";
		String sensor = "sensor1";
		SqlSession session = sqlSessionFactory.openSession();
		
//		MetadataModelMapper metadataModelMapper = session.getMapper(MetadataModelMapper.class);
//		
//		
//		
//		MetadataModel metadataModel = metadataModelMapper.selectByPrimaryKey(filePath);
//		System.out.println("元数据的卫星是："+metadataModel.getStatellite());
//		
//		
//		
//		List<MetadataModel> list = metadataModelMapper.selectByDate(times);
//		for(int i=0;i<list.size();i++){
//			System.out.println(list.get(i).getFileUrl());
//		}
//		
//		
//		
//		List<MetadataModel> list1 = metadataModelMapper.selectByRegion(region);
//		for(int i=0;i<list1.size();i++){
//			System.out.println(list1.get(i).getFileUrl());
//		}
//		
//		List<MetadataModel> list3 = metadataModelMapper.selectByStatellite(statellite);
//		System.out.println("====按卫星查询====");
//	 	for(int i=0;i<list3.size();i++){
//			System.out.println(list3.get(i).getFileUrl());
//		}
//		
//		List<MetadataModel> list2 = metadataModelMapper.selectByStatelliteAndSensor(statellite, sensor);
//		System.out.println("====按卫星和传感器查询====");
//	 	for(int i=0;i<list2.size();i++){
//			System.out.println(list1.get(i).getFileUrl());
//		}
//	 	
//		List<MetadataModel> list4 = metadataModelMapper.selectBySTSR(statellite, sensor, times, region);
//		System.out.println("====按卫星、传感器、时间、地址查询====");
//	 	for(int i=0;i<list4.size();i++){
//			System.out.println(list4.get(i).getFileUrl());
//		}
//	 	
//		List<MetadataModel> list5 = metadataModelMapper.selectByTimeAndRegion(times, region);
//		System.out.println("====按时间、地址查询====");
//	 	for(int i=0;i<list5.size();i++){
//			System.out.println(list5.get(i).getFileUrl());
//		}
//		
//		List<MetadataModel> list6 = metadataModelMapper.selectByTimeAndRegion(times, region);
//		System.out.println("====按时间、卫星查询====");
//	 	for(int i=0;i<list6.size();i++){
//			System.out.println(list6.get(i).getFileUrl());
//		}
//	 	
//		List<MetadataModel> list7 = metadataModelMapper.selectByRegionAndStatellite(region, statellite);
//		System.out.println("====按地域、卫星查询====");
//	 	for(int i=0;i<list7.size();i++){
//			System.out.println(list7.get(i).getFileUrl());
//		}
//	 	
//		List<MetadataModel> list8 = metadataModelMapper.selectByTSR(statellite, region, times);
//		System.out.println("====按时间，地域、卫星查询====");
//	 	for(int i=0;i<list8.size();i++){
//			System.out.println(list8.get(i).getFileUrl());
//		}
//	 	
//		List<MetadataModel> list9 = metadataModelMapper.selectByTSS(statellite, sensor, times);
//		System.out.println("====按时间，传感器、卫星查询====");
//	 	for(int i=0;i<list9.size();i++){
//			System.out.println(list9.get(i).getFileUrl());
//		}
//	 	
//		List<MetadataModel> list10 = metadataModelMapper.selectByRSS(statellite, sensor, region);
//		System.out.println("====按地域，传感器、卫星查询====");
//	 	for(int i=0;i<list10.size();i++){
//			System.out.println(list10.get(i).getFileUrl());
//		}
//	 	
	 	OPStackShareOperater opStackShareOperater = new OPStackShareOperater();
	 	opStackShareOperater.generateTwoD("te1", "region","satellite");
	}

}
