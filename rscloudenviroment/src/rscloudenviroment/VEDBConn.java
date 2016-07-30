package rscloudenviroment;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class VEDBConn  {
	
	   //�������ve���ݿ������
		private static VEDBConfig              dbConfig = null;
		private static ComboPooledDataSource   mccps_ds = null;
	
		
		// Ĭ�����ݿ����ѡ��
		private static String driver;			// �������ݿ�����
		private static String ip;				// Ĭ�����ݿ��������ַ
		private static String port;				// Ĭ�����ݿ�������˿�
		
		private static String mccps_sid;		// Ĭ�����ݿ�SID
		private static String mccps_user;		// Ĭ�Ϸ������ݿ��û�
		private static String mccps_password;	// Ĭ�Ϸ������ݿ��û�����
		
		private static String minPoolSize;		// Ĭ�Ϸ������ݿ���С������
		private static String maxPoolSize;		// Ĭ�Ϸ������ݿ����������
		
		protected boolean bIsConnection;        //�ж��Ƿ����ӳɹ�
		
		
	
	

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
				// Ĭ�����ݿ����ѡ��
				driver         = "com.mysql.jdbc.Driver";	// �������ݿ�����
				//ip             = "10.3.10.1";				// Ĭ�����ݿ��������ַ
				ip             = "localhost";				// Ĭ�����ݿ��������ַ
				port           = "3306";					// Ĭ�����ݿ�������˿�
				
				//mccps_sid      = "mccps";					// Ĭ�����ݿ�SID
				mccps_sid      = "openstack";	
				/*mccps_user     = "caoyang";					// Ĭ�Ϸ������ݿ��û�
				mccps_password = "123456";				// Ĭ�Ϸ������ݿ��û�����
*/				
				mccps_user     = "root";					// Ĭ�Ϸ������ݿ��û�
				mccps_password = "WRognu73";				// Ĭ�Ϸ������ݿ��û�����
				minPoolSize    = "10";						// Ĭ�Ϸ������ݿ���С������ 100
				maxPoolSize    = "20";						// Ĭ�Ϸ������ݿ���������� 500
			}else{
				driver         = "com.mysql.jdbc.Driver";	// �������ݿ�����
				ip             = dbConfig.getIP();			// Ĭ�����ݿ��������ַ
				port           = dbConfig.getPort();		// Ĭ�����ݿ�������˿�
				
				mccps_sid      = dbConfig.getSid();			// Ĭ�����ݿ�SID
				mccps_user     = dbConfig.getUser();		// Ĭ�Ϸ������ݿ��û�
				mccps_password = dbConfig.getPassword();		// Ĭ�Ϸ������ݿ��û�����
				
				minPoolSize    = "10";						// Ĭ�Ϸ������ݿ���С������
				maxPoolSize    = "20";						// Ĭ�Ϸ������ݿ����������
			}
			
			mccps_ds = new ComboPooledDataSource();
			// ����JDBC��Driver��
			mccps_ds.setDriverClass(driver);
			// ����JDBC��URL
			mccps_ds.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + mccps_sid);
			// �������ݿ�ĵ�¼�û���
			mccps_ds.setUser(mccps_user);
			// �������ݿ�ĵ�¼�û�����
			mccps_ds.setPassword(mccps_password);
			// �������ӳص����������
			mccps_ds.setMaxPoolSize(Integer.parseInt(maxPoolSize));
			// �������ӳص���С������
			mccps_ds.setMinPoolSize(Integer.parseInt(minPoolSize));
			// ���ó�ʼ��������
			mccps_ds.setInitialPoolSize( Integer.parseInt( minPoolSize ) );
			// ���õ����ӳ�����ʱ�ͻ��˵���getConnection()��ȴ���ȡ�����ӵ�ʱ�䣬��ʱ���׳�SQLException�� ����Ϊ0�������ڵȴ�����λ���룬Ĭ��Ϊ0
			mccps_ds.setCheckoutTimeout( 0 );
			mccps_ds.setIdleConnectionTestPeriod( 120 );
			
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
	}
	
	// ��ȡ���ݿ�����
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
	//�ж��Ƿ��������ݿ�ɹ�	
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
	

// -------------����------------
	
	public static void main(String[] args){
		VEDBConfig Cfg = null;
		VEDBConn vedb = new VEDBConn(Cfg);
		vedb.getConnection();
	
		RSHostDB rsvedb = new RSHostDB();
		
	/*	//���
	    RSHost rshost1= new RSHost();
		rshost1.setVEid("43ffdd1233ff132");
		rshost1.setVEname("test200");
		rshost1.setVEip("10.3.10.11");
		boolean rs = rsvedb.addHost(rshost1);
		System.out.println("�Ƿ���ӳɹ�-----------"+rs);*/
		
		/*//ɾ��
		String count = "WHERE HostId='dd1233ff132'";
		ArrayList<RSHost> rshostList1 = rsvedb.search(count);
		System.out.println("Ҫɾ��������Ϊ��"+ rshostList1);
		boolean rs = rsvedb.deleteRSHost(rshostList1);*/
		
		//����
		//boolean rs = rsvedb.updateRSHost(rshost)
		
		
		ArrayList<RSHost> rshostList = rsvedb.search(null);
		System.out.println("�����ʾ��"+ rshostList);
		
		boolean resul = vedb.isConnected();
		if(resul = true){
			System.out.println("chengong");
		}else System.out.println("fiale");
		vedb.close();
	
	}
}
