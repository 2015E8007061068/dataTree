package rscloudenviroment;

import org.openstack4j.api.OSClient;

public class RSVEInstance implements VEInstance {
	String VEid;

	String Url;
	
	//��������ʾ
	@Override
	public void VE(VEStudio vestudio) {
		
		
		VEid =vestudio.getVEID();
	
		System.out.println("��Ҫ��ʾ�ķ�����IDΪ"+VEid);
		
		Url = vestudio.getVEURL();
		
		System.out.println("vnc�ĵ�ַΪ��"+Url);
		
		
	}
 
	//�洢��ʾ
	@Override
	public void Storage(VEStudio vestudio) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delectVE(String ID) {
		
		
	}

}
