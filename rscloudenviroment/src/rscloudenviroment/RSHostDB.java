package rscloudenviroment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.jdbc.Statement;

//数据库表的名字是    rshostdb


public class RSHostDB extends VEDBConn {
	
	private static Connection conn = null;
	private String dbTable;
	
/*	//日志记录  ------------空
	private Logger logger = SystemLogger.getSysLogger();*/
	
	

	public RSHostDB() {
	  System.out.println("RSHostDB.RSHostDB()");
	  
		// 初始化连接数据库
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
	// 向云主机库插入云主机
	public synchronized boolean addHost(RSHost host) {
		System.out.println("RSHostDB.addHost(RSHost host)");
		if (null==host) {
//			logger.error("云主机为空，插入失败！");
			System.out.println("云主机为 空，插入失败");
			return false;
		}
		try{
			String strSql = "INSERT INTO "
					+ this.dbTable
					+"(HostId,HostName,HostIp,HostUrl,HostMessage,HostStatus,HostOwer)"
					+"VALUES(?,?,?,?,?,?,?)";
			if (null == conn) {
				System.out.println("连接数据库失败");
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
			
			// 执行插入
			int count = pstmt.executeUpdate();
			if (count == 0) {
				System.out.println("error-----插入更新行数为0，请检查对象参数!");
			}
			// 关闭相关连接
			pstmt.close();
		}catch (SQLException e) {
			
			System.out.println("插入用主机失败");
			System.out.println("sql执行异常"+e);
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
		    	System.out.println("数据初始化失败");
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
		    	System.out.println("实例显示为空！检索条件："+condition+"存在问题。");
		    	
		    }
		    st.close();
		    rs.close();
		}catch (SQLException e){
			System.out.println("查询操作失败");
			System.out.println("SQL执行异常"+e);
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
	
	//通过VEid得到实例云主机
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
	//通过主机名称和ip获取实例云主机
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
	
	//更新实例云主机
	public synchronized boolean updateRSHost(RSHost rshost){
		System.out.println("RSHostDB.updateRSHost(rshost)");
		if(null==rshost){
			System.out.println("遥感主机为空，更新失败");
			return false;
		}
		try{
			String strSql = "UPDATE"
					+ this.dbTable
					+ "SET HostId = ? ,HostName = ? ,HostIp = ? ,"
					+ "HostUrl = ? ,HostMessage = ? ,HostStatus = ? ,HostOwer = ? , WHERE HostId = ?";
			
			if(null == conn){
				System.out.println("链接数据库失败！");
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
			
			
			// 执行插入
			// pstmt.execute();
			//执行更新
			int count = pstmt.executeUpdate();
			if(count == 0 ){
				System.out.println("更新行数为0，请检查遥感主机参数是否合法！");
			}
		
			//关闭相关链接
			pstmt.close();
		} catch (SQLException e){
			System.out.println("更新云主机实例失败");
			System.out.println("SQL语句执行异常"+e);
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
			System.out.println("云主机实例列表为空，删除云主机失败");
			return false;
		}
		try{
			
				String strSql = "DELETE FROM " + this.dbTable
						+ " WHERE HostId = ?";
				if(null == conn){
					System.out.println("数据库初始化失败");
					return false;
				}
				PreparedStatement pstmt = conn.prepareStatement(strSql);
				pstmt.setString(1, rshost.VEid);
				//执行更新
				int count = pstmt.executeUpdate();
				if(count==0){
					System.out.println("删除云主机行数为0");
				}
				//关闭相关链接
				pstmt.close();
			 
			}catch(SQLException e){
				System.out.println("删除云主机失败");
				System.out.println("SQL语句执行异常"+e);
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
	//删除云主机实例
	public synchronized boolean deleteRSHost(ArrayList<RSHost> rshostList){
		
		System.out.println("RSHostDB.deleteRSHost(rshostList)");
		if(null == rshostList){
			System.out.println("云主机实例列表为空，删除云主机失败");
			return false;
		}
		try{
			Iterator<RSHost> rshost = rshostList.iterator();
			while(rshost.hasNext()){
				String strSql = "DELETE FROM " + this.dbTable
						+ " WHERE HostId = ?";
				if(null == conn){
					System.out.println("数据库初始化失败");
					return false;
				}
				PreparedStatement pstmt = conn.prepareStatement(strSql);
				pstmt.setString(1, rshost.next().VEid);
				//执行更新
				int count = pstmt.executeUpdate();
				if(count==0){
					System.out.println("删除云主机行数为0");
				}
				//关闭相关链接
				pstmt.close();
			 }
			}catch(SQLException e){
				System.out.println("删除云主机失败");
				System.out.println("SQL语句执行异常"+e);
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
