package rscloudenviroment;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class VEDBConn  {
	
	   //用来存放ve数据库的配置
		private static VEDBConfig              dbConfig = null;
		private static ComboPooledDataSource   mccps_ds = null;
	
		
		// 默认数据库参数选项
		private static String driver;			// 连接数据库驱动
		private static String ip;				// 默认数据库服务器地址
		private static String port;				// 默认数据库服务器端口
		
		private static String mccps_sid;		// 默认数据库SID
		private static String mccps_user;		// 默认访问数据库用户
		private static String mccps_password;	// 默认访问数据库用户密码
		
		private static String minPoolSize;		// 默认访问数据库最小连接数
		private static String maxPoolSize;		// 默认访问数据库最大连接数
		
		protected boolean bIsConnection;        //判断是否连接成功
		
		
	
	

	public VEDBConn() {
		System.out.println("VEDBConn.VEDBConn()");
	//	dbConfig = null;
		this.bIsConnection = false;
	}

	public VEDBConn(VEDBConfig Config){
		System.out.println("VEDBConn.VEDBConn(VEDBConfig Config)");
		dbConfig = Config;
		this.bIsConnection = false;
		try{
			mccps_ds = null;
			
			if( null == dbConfig ){
				// 默认数据库参数选项
				driver         = "com.mysql.jdbc.Driver";	// 连接数据库驱动
				//ip             = "10.3.10.1";				// 默认数据库服务器地址
				ip             = "localhost";				// 默认数据库服务器地址
				port           = "3306";					// 默认数据库服务器端口
				
				//mccps_sid      = "mccps";					// 默认数据库SID
				mccps_sid      = "openstack";	
				/*mccps_user     = "caoyang";					// 默认访问数据库用户
				mccps_password = "123456";				// 默认访问数据库用户密码
*/				
				mccps_user     = "root";					// 默认访问数据库用户
				mccps_password = "WRognu73";				// 默认访问数据库用户密码
				minPoolSize    = "10";						// 默认访问数据库最小连接数 100
				maxPoolSize    = "20";						// 默认访问数据库最大连接数 500
			}else{
				driver         = "com.mysql.jdbc.Driver";	// 连接数据库驱动
				ip             = dbConfig.getIP();			// 默认数据库服务器地址
				port           = dbConfig.getPort();		// 默认数据库服务器端口
				
				mccps_sid      = dbConfig.getSid();			// 默认数据库SID
				mccps_user     = dbConfig.getUser();		// 默认访问数据库用户
				mccps_password = dbConfig.getPassword();		// 默认访问数据库用户密码
				
				minPoolSize    = "10";						// 默认访问数据库最小连接数
				maxPoolSize    = "20";						// 默认访问数据库最大连接数
			}
			
			mccps_ds = new ComboPooledDataSource();
			// 设置JDBC的Driver类
			mccps_ds.setDriverClass(driver);
			// 设置JDBC的URL
			mccps_ds.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + mccps_sid);
			// 设置数据库的登录用户名
			mccps_ds.setUser(mccps_user);
			// 设置数据库的登录用户密码
			mccps_ds.setPassword(mccps_password);
			// 设置连接池的最大连接数
			mccps_ds.setMaxPoolSize(Integer.parseInt(maxPoolSize));
			// 设置连接池的最小连接数
			mccps_ds.setMinPoolSize(Integer.parseInt(minPoolSize));
			// 设置初始化连接数
			mccps_ds.setInitialPoolSize( Integer.parseInt( minPoolSize ) );
			// 设置当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException， 如设为0则无限期等待。单位毫秒，默认为0
			mccps_ds.setCheckoutTimeout( 0 );
			mccps_ds.setIdleConnectionTestPeriod( 120 );
			
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
	}
	
	// 获取数据库连接
	public static synchronized  Connection getConnection(){
		System.out.println("VEDBConn.getConnection()");
		Connection conn = null;
		try{
			conn = mccps_ds.getConnection();
		}catch (SQLException e ){
			e.printStackTrace();
			System.out.println("<error> VEDBConn.getConnection()");
			return getConnection();
		}
		return conn;
	}
	//判断是否连接数据库成功	
	public boolean isConnected(){
		System.out.println("VEDBConn.isConnected()");
		return this.bIsConnection;
	}
	
	
	public static synchronized void close(){
		if( null != mccps_ds ){
			mccps_ds.close();
			mccps_ds = null;
		}
	}
	

// -------------测试------------
	
	public static void main(String[] args){
		VEDBConfig Cfg = null;
		VEDBConn vedb = new VEDBConn(Cfg);
		vedb.getConnection();
	
		RSHostDB rsvedb = new RSHostDB();
		
	/*	//添加
	    RSHost rshost1= new RSHost();
		rshost1.setVEid("43ffdd1233ff132");
		rshost1.setVEname("test200");
		rshost1.setVEip("10.3.10.11");
		boolean rs = rsvedb.addHost(rshost1);
		System.out.println("是否添加成功-----------"+rs);*/
		
		/*//删除
		String count = "WHERE HostId='dd1233ff132'";
		ArrayList<RSHost> rshostList1 = rsvedb.search(count);
		System.out.println("要删除的数据为："+ rshostList1);
		boolean rs = rsvedb.deleteRSHost(rshostList1);*/
		
		//更改
		//boolean rs = rsvedb.updateRSHost(rshost)
		
		
		ArrayList<RSHost> rshostList = rsvedb.search(null);
		System.out.println("结果显示："+ rshostList);
		
		boolean resul = vedb.isConnected();
		if(resul = true){
			System.out.println("chengong");
		}else System.out.println("fiale");
		vedb.close();
	
	}
}
