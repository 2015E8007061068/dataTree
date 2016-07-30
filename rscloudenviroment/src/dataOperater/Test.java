package dataOperater;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

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
		SqlSession session = sqlSessionFactory.openSession();
		
		MetadataModelMapper metadataModelMapper = session.getMapper(MetadataModelMapper.class);
		
		MetadataModel metadataModel = metadataModelMapper.selectByPrimaryKey(filePath);
		System.out.println("元数据的卫星是："+metadataModel.getStatellite());
		
	}

}
