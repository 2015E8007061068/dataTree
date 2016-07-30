package rscloudenviroment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.compute.Address;
import org.openstack4j.model.compute.Addresses;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.storage.block.Volume;
import org.openstack4j.model.storage.object.SwiftAccount;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.openstack.OSFactory;

public class VETest {

	private static String hostname;

	public static void main(String[] args) {
		System.out.println("1  运行创建模板遥感云主机例子：");
		//run1();
		System.out.println("2  运行创建个性化遥感云主机例子：");
		//run2();

		System.out.println("Sysout+提示");
		//run3();
		/*SSHLink ss = new SSHLink();
		ss.command("dd");
		String openstackServerName = "linux";
		String cmd = "slat "+"'"+openstackServerName+"'"+"state.sls"+" httpd;";
		System.out.println(cmd);*/
		
		//run4();
		//test(); //测试创建
		//hostname();
		//storagetest("test");
		VEStudio vs = StudioFactory.creator("RSVE");
		RSVEStudio vs1= new RSVEStudio((RSVEStudio) vs);
		vs1.commd();
		//dbstudio();
	}

	private static void run1() {
		// TODO Auto-generated method stub
	
		VERequest RSVERequest = RequestFactory.creator("RSVE");
		RSVERequest.setVEName("test001");
		RSVERequest.setVEImage("linux32");
		
		RSVERequest re1 = new RSVERequest((rscloudenviroment.RSVERequest) RSVERequest);
		
		re1.setDisk("1024");
		re1.setSoftWare("pipsbig");
		re1.setSwap("4");
		re1.setVcpu("4");
	//	re1.setRxtxVEtype("false");
		VEStudio rSVeStudio = StudioFactory.creator("RSVE");
		System.out.println("得到云主机配置参数");
		try {
			rSVeStudio.parsingXMl(re1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		RSVEStudio st1 = null;
		try {
			st1 = new RSVEStudio((rscloudenviroment.RSVEStudio) rSVeStudio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(st1.validation("demo","WRognu73")== true){
	
		st1.buildBaseVE();
		}
		else
			System.out.println("认证有问题");
		
		VEInstance rSVeInstance = InstanceFactory.creator("RSVE");
		rSVeInstance.VE(st1);
		
	}
	private static void run2() {
		// TODO Auto-generated method stub
		
		VERequest RSVERequest = RequestFactory.creator("RSVE");
		RSVERequest.setVEName("good2");
		RSVERequest.setVEImage("linux1");
		
		
		RSVERequest sq2 = new RSVERequest((rscloudenviroment.RSVERequest) RSVERequest);
		sq2.setSoftWare("pips");
		sq2.setDisk("512");
	
		sq2.setSwap("6");
		sq2.setVcpu("6");
	//	sq2.setRxtxVEtype("false");
		VEStudio rSVeStudio = StudioFactory.creator("RSVE");
		System.out.println("得到云主机配置参数");
		try {
			rSVeStudio.parsingXMl(sq2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		RSVEStudio st2 = null;
		try {
			st2 = new RSVEStudio((rscloudenviroment.RSVEStudio) rSVeStudio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(st2.validation("demo","WRognu73")== true){
		st2.checkImage();
		System.out.println("完成检查镜像库");
		st2.buildBaseVE();
		st2.installSoftware();
		st2.packageVEImage();
		st2.registerVEImage();
		
		}
		else
			System.out.println("认证出现问题");
		
		VEInstance rSVeInstance = InstanceFactory.creator("RSVE");
		rSVeInstance.VE(st2);
		
	}
	
	private static void run3() {
		
		System.out.println(RSTemplate.English);
		VERequest RSVERequest = RequestFactory.creator("RSVE");
		
		
		RSVERequest sq2 = new RSVERequest((rscloudenviroment.RSVERequest) RSVERequest);
		sq2.setDisk("512");
		sq2.setSoftWare("pipsbig");
		sq2.setSwap("6");
		sq2.setVcpu("4");
//		sq2.setRxtxVEtype("false");
		sq2.setVEImage("linuxzuihouyige");
		sq2.setVEName("testlast");
		try {
			String abc = sq2.toXML();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VEStudio rSVeStudio = StudioFactory.creator("RSVE");
		System.out.println("得到云主机配置参数zuihou");
		try {
			rSVeStudio.parsingXMl(sq2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
	}
	
	public static void run4(){
		VEStudio rSVeStudio = StudioFactory.creator("RSVE");
		RSVEStudio rss = new RSVEStudio((rscloudenviroment.RSVEStudio) rSVeStudio);
		rss.validation("pipsCloud","pipsCloudTeam");
		rss.test();
		
	}

	public static void test(){
		OSClient os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials("pipsCloud","pipsCloudTeam").tenantName("pipsCloud")
				.authenticate();
		System.out.println("完成认证");
		
		String instanceID = "12";
			String openstackZone = "nova";
		String openstackFlavorID="4";
		String openstackServerName="lpf003";
		String openstackImageID="75c6d660-a35d-4502-ab9d-7acbfe175db5";
		String openstackNetworkId = "5511543f-3ec1-4b68-8dca-30ed2ebb9b71";//public
		List<String> openStackNetworkList=new ArrayList<String>();
		openStackNetworkList.add(openstackNetworkId);
		
		ServerCreate sc = Builders.server().name(openstackServerName)
                .flavor(openstackFlavorID)
                .image(openstackImageID)
                .networks(openStackNetworkList)
                .availabilityZone(openstackZone)
                .build();
		System.out.println(openStackNetworkList);
		//---测试阶段不真正创建--------
		Server server = os.compute().servers().boot(sc);
		
		//------通过主机名 确定主机的id----instanceID
		List<? extends Server> servers = os.compute().servers().list();
		int max = servers.size()-1;
		System.out.println(max);
		for(int i =max ;i >=0;){
			if(servers.get(i).getName().equals(openstackServerName)){
				 instanceID = servers.get(i).getId();
				System.out.println("创建的实例id"+instanceID);
			}
			i--;
		}
		
		for(int i=0;i<=50;i++){
			   Server intance = os.compute().servers().get(instanceID);
			     Status status = intance.getStatus();

			     Status ACTIVE = Status.ACTIVE;
			
			try {
				Thread.sleep(6*1000);
				System.out.println(status);
				System.out.println(ACTIVE);
				System.out.println(i+"创建中...");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(status ==ACTIVE){
				System.out.println("创建成功");
           break;
		     }
		}
		
	
		 Server intance = os.compute().servers().get(instanceID);	
	//	 String IP = intance.getAccessIPv4();
		 String IP = intance.getAddresses().getAddresses("public").get(0).getAddr();
		 System.out.println(IP);
		 for(int j=0;j<10;j++){
			 hostname = "host-"+IP.replace(".", "-");
			 
		}	
			
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public static void test1(){
		
		OSClient os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials("pipsCloud","pipsCloudTeam").tenantName("pipsCloud")
				.authenticate();
		System.out.println("完成认证");
		List<? extends Server> servers = os.compute().servers().list();
		int max = servers.size()-1;
		System.out.println(max);
		for(int i =max ;i >=0;){
			if(servers.get(i).getName().equals("lpf")){
				 String instanceID = servers.get(i).getId();
				// servers.get(i).getHost().getBytes();
			
				String hostname = servers.get(i).getAddresses().getAddresses("public").get(0).getAddr();
				
				String host=null;
				for(int i1=0;i1<10;i1++){
					 host = "host-"+hostname.replace(".", "-");
				}
				
				//System.out.println("创建的实例id"+instanceID);
				System.out.println("创建的实例:"+host);
			}
			i--;
		}
		
		String openstackNetworkId = "5511543f-3ec1-4b68-8dca-30ed2ebb9b71";//public
		List<String> openStackNetworkList=new ArrayList<String>();
		openStackNetworkList.add(openstackNetworkId);
		System.out.println(openstackNetworkId);
		
		
		 //----------openstack镜像库方法解析镜像-------------
		String imageID1 = null;
		String osID="CentOS-oracle-PIPS";
		List<? extends Image> images = os.images().list();
		System.out.println(images);
		int max1 = images.size()-1;
		System.out.println(max1);
		for(int i =max1 ;i >=0;){
		//	System.out.println(i);
			if(images.get(i).getName().equals(osID)){
				 imageID1 = images.get(i).getId();
				System.out.println("得到镜像id"+imageID1);
			}
			i--;
		}
		

	/*	String openstackZone = "nova";
		String openstackFlavorID="4";
		String openstackServerName="lpf003";
		String openstackImageID="75c6d660-a35d-4502-ab9d-7acbfe175db5";
		ServerCreate sc = Builders.server().name(openstackServerName)
                .flavor(openstackFlavorID)
                .image(openstackImageID)
                .networks(openStackNetworkList)
                .availabilityZone(openstackZone)
                .build();
		System.out.println(openStackNetworkList);
		//---测试阶段不真正创建--------
		Server server = os.compute().servers().boot(sc);*/
		
		
		//------通过主机名 确定主机的id----instanceID
		List<? extends Server> servers1 = os.compute().servers().list();
		
		int max11 = servers1.size()-1;
		System.out.println(max11);
		for(int i =max11 ;i >=0;){
			if(servers1.get(i).getName().equals("lpf003")){
				
				//得到创建实例的全部信息
				Server VEmessage = servers1.get(i);
				//得到创建的实例的ID
				 String instanceID = servers1.get(i).getId();
				// servers.get(i).getHost().getBytes();
				System.out.println("创建的实例id"+instanceID);
				//创建的基础实例的网络是 public（目前只有这一个）
				String host1 = "";
		
				System.out.println(host1);
				
				host1 = servers1.get(i).getAddresses().getAddresses("public").get(0).getAddr();
				for(int j=0;j<10;j++){
					 hostname = "host-"+host1.replace(".", "-");
					 
				}
				
				
				System.out.println("创建的实例的系统名称是："+hostname);
			}
			i--;
		}
	}

	public static void hostname(){
		
		OSClient os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials("pipsCloud","pipsCloudTeam").tenantName("pipsCloud")
				.authenticate();
		System.out.println("完成认证");
		
		
		List<? extends Server> servers1 = os.compute().servers().list();
		
		int max11 = servers1.size()-1;
		System.out.println(max11);
		for(int i =max11 ;i >=0;){
			if(servers1.get(i).getName().equals("PIPS_Test")){
				
				//得到创建实例的全部信息
				Server VEmessage = servers1.get(i);
				//得到创建的实例的ID
				 String instanceID = servers1.get(i).getId();
				// servers.get(i).getHost().getBytes();
				System.out.println("创建的实例id"+instanceID);
				//创建的基础实例的网络是 public（目前只有这一个）
				String host1 = "";
		
				System.out.println(host1);
				String hostname1= servers1.get(i).getAddresses().getAddresses("private").get(0).getAddr();
				System.out.println("得到的主机名称"+hostname1);
				host1 = servers1.get(i).getAddresses().getAddresses("Private").get(0).getAddr();
				for(int j=0;j<10;j++){
					 hostname = "host-"+host1.replace(".", "-");
					 
				}
				
				
				System.out.println("创建的实例的系统名称是："+hostname);
			}
			i--;
		}
	}
	public static void storagetest(String storageName){
		int storageID;
		OSClient os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials("pipsCloud","pipsCloudTeam").tenantName("pipsCloud")
				.authenticate();
		System.out.println("完成认证");
		
		SwiftAccount account = os.objectStorage().account().get();
		int size = (int) account.getContainerCount();
		System.out.println(size);
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		System.out.println(account);
		System.out.println(containers);
		/*List<? extends Volume> volumes = os.blockStorage().volumes().list();
		System.out.println(volumes);*/
		boolean success = false;
		for(int i = 0; i<size; i++){
			if (containers.get(i).getName().equals(storageName)){
				System.out.println("云盘名称已经存在，请更换名称");
				success = true;	
			}
		}
		if( success == true){
			System.out.println("云盘名称已经存在，请更换名称");
		}else{
		os.objectStorage().containers().create("test");
		System.out.println("创建容器存储"+storageName+"成功");
		}
		
		for(int i = 0; i<size; i++){
			if (containers.get(i).getName().equals(storageName)){
				System.out.println("返回容器ID");
				storageID = i;
				System.out.println(i);
			}
		}
		
		/*List<? extends SwiftObject> objs = os.objectStorage().objects().list("test");
		System.out.println(objs);
		URL url = null;
		try {
			url = new URL("file:///C:/kms8.log");
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} //file:///C:/kms8.log;
		String etag = os.objectStorage().objects().put("test/lpf", "lpf2", Payloads.create(url));
		System.out.println(etag)*/;
		System.out.println("xiayibu");
		//虚拟目录还有问题
		os.objectStorage().containers().createPath(storageName, "");
		os.objectStorage().containers().createPath(storageName, "");
		//os.objectStorage().containers().createPath(storageName, storageName);
		
	}

	public static void dbstudio(){
		RSHost rshost= new RSHost();
		String vename = "dfsa";
		rshost.setVEname(vename);
		String software = "dfs";
		rshost.setVEmessage("安装遥感软件有："+software);
		String VEID = "dsf";
		rshost.setVEid(VEID);
		String VNCUrl = "dsf";
		rshost.setVEurl(VNCUrl);
		
		VEDBConfig Cfg = null;
		VEDBConn vedb = new VEDBConn(Cfg);
		RSHostDB rsvedb = new RSHostDB();
		boolean rs = rsvedb.addHost(rshost);
		boolean de =rsvedb.deleteRSHost(rshost);
		
		System.out.println(de);
		
		
	}
}
