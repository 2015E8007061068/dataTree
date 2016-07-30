package rscloudenviroment;

import java.io.IOException;
import java.util.List;

import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;

public class RSTest {

	

	/*public static void main(String[] args) {
		

		String cpu = "2";
		String ram = "4096";
		String storage = "40";
		String name ="test001";
		String software="gdal";
		String databases= "mysql";
		String osid= "centOS6.6";
		
		
		
		VERequest RSVERequest = RequestFactory.creator("RSVE");
		//复写构造函数
		RSVERequest re1 = new RSVERequest((rscloudenviroment.RSVERequest) RSVERequest);
		
		re1.setVEName(name);
		re1.setOS(osid);
		re1.setVcpu(cpu);
		re1.setRam(ram);
		re1.setDatabase(databases);
		re1.setDisk(storage);
		re1.setRSsoftware(software);
		
		re1.setVEImage("xx");
		re1.setSwap("xx");
		re1.setCommpile("xx");
		re1.setPFS("xx");
		
		try {
			re1.toXML();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		VEStudio rSVeStudio = StudioFactory.creator("RSVE");
		RSVEStudio st1 = new RSVEStudio((rscloudenviroment.RSVEStudio) rSVeStudio);
		
		try {
			st1.parsingXMl(re1);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		if(st1.checkImage()==true){
			st1.buildVE();
		}
		else{
			st1.buildBaseVE();
			st1.installSoftware();
			
			
			st1.packageVEImage();
			//管理员操作的
			st1.registerVEImage();
		}
		
		
	}*/
	
	public static void main(String[] args) {
		VEStudio rSVeStudio = StudioFactory.creator("RSVE");
		RSVEStudio st1 = new RSVEStudio((rscloudenviroment.RSVEStudio) rSVeStudio);
	//	st1.checkimage("2f2abe8e-771e-412e-a17e-b58797557c7c", "快照修改");
		st1.validation("pipsCloud", "pipsCloudTeam");
		
		List<? extends Server> servers = st1.os.compute().servers().list();
		System.out.println(servers);
		String instanceID= "8db98a3a-bbb2-4b8c-9797-450345143320";
		Server intance = st1.os.compute().servers().get(instanceID);
	     Status status = intance.getStatus();
	     System.out.println(status);
	     System.out.println(Status.ACTIVE);
	     Status ACTIVE = Status.ACTIVE;
		if(status ==ACTIVE){
			System.out.println("123");
	    	 
	     }
		for(int i=0;i<=3;i++){
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(status ==ACTIVE){
				System.out.println("123");
              break;
		     }
		}
		
	}

}
