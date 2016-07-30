package rscloudenviroment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.jdbc.Statement;

//���ݿ���������    rshostdb


public class RSHostDB extends VEDBConn {
	
	private static Connection conn = null;
	private String dbTable;
	
/*	//��־��¼  ------------��
	private Logger logger = SystemLogger.getSysLogger();*/
	
	

	public RSHostDB() {
	  System.out.println("RSHostDB.RSHostDB()");
	  
		// ��ʼ���������ݿ�
		if (null == conn) {
			conn = getConnection();
			if (conn == null) {
				this.bIsConnection = false;
				System.out.println("error-----RSHostDB.RSHostDB()| conn = getConnetcion()|conn = null");
			} else {
				this.bIsConnection = true;
			}
		}
		this.dbTable = "rshostdb";
	}
	public static synchronized void closeConnected() {
		try {
			if (null != conn) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	// �������������������
	public synchronized boolean addHost(RSHost host) {
		System.out.println("RSHostDB.addHost(RSHost host)");
		if (null==host) {
//			logger.error("������Ϊ�գ�����ʧ�ܣ�");
			System.out.println("������Ϊ �գ�����ʧ��");
			return false;
		}
		try{
			String strSql = "INSERT INTO "
					+ this.dbTable
					+"(HostId,HostName,HostIp,HostUrl,HostMessage,HostStatus,HostOwer)"
					+"VALUES(?,?,?,?,?,?,?)";
			if (null == conn) {
				System.out.println("�������ݿ�ʧ��");
				return false;
			}
			PreparedStatement pstmt = conn.prepareStatement(strSql);
			pstmt.setString(1, host.VEid);
			pstmt.setString(2, host.VEname);
			pstmt.setString(3, host.VEip);
			pstmt.setString(4, host.VEurl);
			pstmt.setString(5, host.VEmessage);
			pstmt.setString(6, host.VEstatus);
			pstmt.setString(7, host.VEowner);
			
			// ִ�в���
			int count = pstmt.executeUpdate();
			if (count == 0) {
				System.out.println("error-----�����������Ϊ0������������!");
			}
			// �ر��������
			pstmt.close();
		}catch (SQLException e) {
			
			System.out.println("����������ʧ��");
			System.out.println("sqlִ���쳣"+e);
			String strSqlState = e.getSQLState();
			if (strSqlState.equals("08S01")) {
				conn = getConnection();
				return addHost(host);
			}
			e.printStackTrace();
			return false;
		}
		
		
		return true;
		
	}
	
	public synchronized ArrayList<RSHost> search(String condition){
		System.out.println("RSHostDB.search(String)");
		
		if(null == condition){
			condition="";
		}
		ArrayList<RSHost> hostList = new ArrayList<RSHost>();
		RSHost rshost = null;
		try{
			
			String strSql = "SELECT * FROM " + this.dbTable + " " + condition;
		    System.out.println(strSql);
			if (null == conn){
		    	System.out.println("���ݳ�ʼ��ʧ��");
		    	return hostList;
		    }
		    java.sql.Statement st = conn.createStatement();
		    ResultSet rs = st.executeQuery(strSql);
		    boolean flag = false;
		    
		    while(rs.next()){
		    	flag = true;
		    	rshost = new RSHost();
		    	rshost.VEid = rs.getString("HostId");
		    	rshost.VEname = rs.getString("HostName");
		    	rshost.VEip = rs.getString("HostIp");
		    	rshost.VEurl = rs.getString("HostUrl");
		    	rshost.VEmessage = rs.getString("HostMessage");
		    	rshost.VEstatus = rs.getString("HostStatus");
		    	
		    	rshost.VEowner = rs.getString("HostOwer");
		    	
		    	hostList.add(rshost);
		    			
		    }
		    if(!flag){
		    	System.out.println("ʵ����ʾΪ�գ�����������"+condition+"�������⡣");
		    	
		    }
		    st.close();
		    rs.close();
		}catch (SQLException e){
			System.out.println("��ѯ����ʧ��");
			System.out.println("SQLִ���쳣"+e);
			String strSqlState = e.getSQLState();
			if (strSqlState.equals("08S01")) {
				conn = getConnection();
				return search(condition);
			}
			e.printStackTrace();
			hostList.clear();
			return hostList;
		}
		return hostList;
	}
	
	//ͨ��VEid�õ�ʵ��������
	public synchronized RSHost getVE(String strVEid){
		System.out.println("RSHostDB.getVE(VEid)");
		RSHost rshost = null;
		String strQuery = "WHERE HostId = '"+strVEid+"'";
		ArrayList<RSHost> hostList = this.search(strQuery);
		if(!hostList.isEmpty()){
			rshost = hostList.get(0);
		}
		return rshost;
	}
	//ͨ���������ƺ�ip��ȡʵ��������
	public synchronized RSHost getVEbyname(String strVEname,String strVEip){
		System.out.println("RSHostDB.getVEbyname(VEname,VEip)");
		RSHost rshost = null;
		String strQuery = "WHERE HostId = '"+strVEname+"' and HostIp = '"+strVEip+"'";
		ArrayList<RSHost> hostList = this.search(strQuery);
		if(!hostList.isEmpty()){
			rshost = hostList.get(0);
		}
		return rshost;
	}
	
	//����ʵ��������
	public synchronized boolean updateRSHost(RSHost rshost){
		System.out.println("RSHostDB.updateRSHost(rshost)");
		if(null==rshost){
			System.out.println("ң������Ϊ�գ�����ʧ��");
			return false;
		}
		try{
			String strSql = "UPDATE"
					+ this.dbTable
					+ "SET HostId = ? ,HostName = ? ,HostIp = ? ,"
					+ "HostUrl = ? ,HostMessage = ? ,HostStatus = ? ,HostOwer = ? , WHERE HostId = ?";
			
			if(null == conn){
				System.out.println("�������ݿ�ʧ�ܣ�");
				return false;
			}
			PreparedStatement pstmt = conn.prepareStatement(strSql);
			
			int  iIndex = 0;
			pstmt.setString(++iIndex, rshost.VEid);
			pstmt.setString(++iIndex, rshost.VEname);
			pstmt.setString(++iIndex, rshost.VEip);
			pstmt.setString(++iIndex, rshost.VEurl);
			pstmt.setString(++iIndex, rshost.VEmessage);
			pstmt.setString(++iIndex, rshost.VEstatus);
			pstmt.setString(++iIndex, rshost.VEowner);
			
			
			// ִ�в���
			// pstmt.execute();
			//ִ�и���
			int count = pstmt.executeUpdate();
			if(count == 0 ){
				System.out.println("��������Ϊ0������ң�����������Ƿ�Ϸ���");
			}
		
			//�ر��������
			pstmt.close();
		} catch (SQLException e){
			System.out.println("����������ʵ��ʧ��");
			System.out.println("SQL���ִ���쳣"+e);
			String strSqlState = e.getSQLState();
			if (strSqlState.equals("08S01")) {
				conn = getConnection();
				return updateRSHost(rshost);
			}
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	public synchronized boolean deleteRSHost(RSHost rshost){
		if(null == rshost){
			System.out.println("������ʵ���б�Ϊ�գ�ɾ��������ʧ��");
			return false;
		}
		try{
			
				String strSql = "DELETE FROM " + this.dbTable
						+ " WHERE HostId = ?";
				if(null == conn){
					System.out.println("���ݿ��ʼ��ʧ��");
					return false;
				}
				PreparedStatement pstmt = conn.prepareStatement(strSql);
				pstmt.setString(1, rshost.VEid);
				//ִ�и���
				int count = pstmt.executeUpdate();
				if(count==0){
					System.out.println("ɾ������������Ϊ0");
				}
				//�ر��������
				pstmt.close();
			 
			}catch(SQLException e){
				System.out.println("ɾ��������ʧ��");
				System.out.println("SQL���ִ���쳣"+e);
				String strSqlState = e.getSQLState();
				int iErrorCode = e.getErrorCode();
				System.out
						.println("<Error>RSHostDB::deleteRSHost | SQL error code : "
								+ iErrorCode + " | SQL State : " + strSqlState);
				if (strSqlState.equals("08S01")) {
					conn = getConnection();
					return deleteRSHost(rshost);
				}
				e.printStackTrace();
				return false;
			}
		return true;
		
	}
	//ɾ��������ʵ��
	public synchronized boolean deleteRSHost(ArrayList<RSHost> rshostList){
		
		System.out.println("RSHostDB.deleteRSHost(rshostList)");
		if(null == rshostList){
			System.out.println("������ʵ���б�Ϊ�գ�ɾ��������ʧ��");
			return false;
		}
		try{
			Iterator<RSHost> rshost = rshostList.iterator();
			while(rshost.hasNext()){
				String strSql = "DELETE FROM " + this.dbTable
						+ " WHERE HostId = ?";
				if(null == conn){
					System.out.println("���ݿ��ʼ��ʧ��");
					return false;
				}
				PreparedStatement pstmt = conn.prepareStatement(strSql);
				pstmt.setString(1, rshost.next().VEid);
				//ִ�и���
				int count = pstmt.executeUpdate();
				if(count==0){
					System.out.println("ɾ������������Ϊ0");
				}
				//�ر��������
				pstmt.close();
			 }
			}catch(SQLException e){
				System.out.println("ɾ��������ʧ��");
				System.out.println("SQL���ִ���쳣"+e);
				String strSqlState = e.getSQLState();
				int iErrorCode = e.getErrorCode();
				System.out
						.println("<Error>RSHostDB::deleteRSHost | SQL error code : "
								+ iErrorCode + " | SQL State : " + strSqlState);
				if (strSqlState.equals("08S01")) {
					conn = getConnection();
					return deleteRSHost(rshostList);
				}
				e.printStackTrace();
				return false;
			}
		return true;
	}
	


}
