package rscloudenviroment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VESoftware {
	
	
	private Connection connection=null;
	private Statement statement=null;
	private ResultSet resultSet=null;
	
	private String user = null ;
	private String password = null;
	private String database = null;
	
	public void DatabaseBean() 
	{
		
		try{
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		statement=connection.createStatement();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	public String DatabaseLink(String ipport,String name,String password){
		
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection=DriverManager.getConnection(ipport,name,password);
			statement=connection.createStatement();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		
		return null;
	}
	
	

	public void usedatabase() {
		String userName = null;
		String userPassword = null ;
		String strSql = "select * from user where uname='"+userName+"' and password='"+userPassword+"'";
		//ִ��Sql��䣬����ResultSet����
			
	}


	public String insert(String name, String url) {
		String strSql = "insert into software(name,url)values('"+name+"','"+url+"')";
		System.out.println("ִ�е�sql���Ϊ��"+strSql);
		
		try {
			resultSet=statement.executeQuery(strSql);
			
			if(resultSet.next()){
			
				connection.close();
			}
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		
		return strSql;
	}

	//--------����������ƴ����ݿ����ɾ��--------
	public String delete() {
		String name = null;
		String strSql = "delete from software where name='"+name+"'";
		System.out.println("ִ�е�sql���Ϊ��"+strSql);
		
		
		return null;
	}

	//--------------�޸�ĳһ������ļ�¼--------------
	public String update() {
		String name = null;
		String Newurl = null;
		String strSql = "update software set url='"+Newurl+"' where name='"+name+"'";
		System.out.println("ִ�е�sql���Ϊ��"+strSql);
		
		
		return null;
	}


	public String select() {
		String name = null ;
		String strSql = "select * from software where name='"+name+"'";
		System.out.println("ִ�е�sql���Ϊ��"+strSql);
		return null;
	}


	public String show() {
		
		return null;
	}

	

}
