package rscloudenviroment;

import org.openstack4j.api.OSClient;

public class RSVEInstance implements VEInstance {
	String VEid;

	String Url;
	
	//云主机显示
	@Override
	public void VE(VEStudio vestudio) {
		
		
		VEid =vestudio.getVEID();
	
		System.out.println("需要显示的服务器ID为"+VEid);
		
		Url = vestudio.getVEURL();
		
		System.out.println("vnc的地址为："+Url);
		
		
	}
 
	//存储显示
	@Override
	public void Storage(VEStudio vestudio) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delectVE(String ID) {
		
		
	}

}
