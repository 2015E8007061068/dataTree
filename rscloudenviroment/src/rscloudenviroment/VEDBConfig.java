package rscloudenviroment;

/*
 * ���ݿ�������Ϣ�� 
 * 1�����
 * */
public class VEDBConfig {
	//����������ݿ��������Ϣ
	String ip;			//���ݿ������ip��ַ
	String port;		//���ݿ�������˿�
	String sid;			//���ݿ����ID
	String user;		//��½�û���
	String password;	//��½����
	String dbtable;		//���ݿ��
	public VEDBConfig(String ip, String port, String sid, String user, String passwd, String dbtable) {
		System.out.println("VEDBConfig.VEDBConfig()");
		this.ip       = ip;
		this.port     = port;
		this.sid      = sid;
		this.user     = user;
		this.password = passwd;
		this.dbtable  = dbtable;		
		
	}
	public String getIP() {
		System.out.println("VEDBConfig.getIP()");
		return ip;
	}
	public void setIP(String ip) {
		System.out.println("VEDBConfig.setIP()");
		this.ip = ip;
	}
	public String getPort() {
		System.out.println("VEDBConfig.getPort()");
		return port;
	}
	public void setPort(String port) {
		System.out.println("VEDBConfig.setPort()");
		this.port = port;
	}
	public String getSid() {
		System.out.println("VEDBConfig.getSid()");
		return sid;
	}
	public void setSid(String sid) {
		System.out.println("VEDBConfig.setSid()");
		this.sid = sid;
	}
	public String getUser() {
		System.out.println("VEDBConfig.getUser()");
		return user;
	}
	public void setUser(String user) {
		System.out.println("VEDBConfig.setUser()");
		this.user = user;
	}
	public String getPassword() {
		System.out.println("VEDBConfig.getPassword()");
		return password;
	}
	public void setPassword(String password) {
		System.out.println("VEDBConfig.setPassword()");
		this.password = password;
	}
	public String getDBtable() {
		System.out.println("VEDBConfig.getDBtable()");
		return dbtable;
	}
	public void setDBtable(String dbtable) {
		System.out.println("VEDBConfig.setDBtable()");
		this.dbtable = dbtable;
	}

}
