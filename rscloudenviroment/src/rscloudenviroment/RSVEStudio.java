
package rscloudenviroment;

import java.io.File;
import java.net.URL;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
//import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;

import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.VNCConsole;
import org.openstack4j.model.compute.VNCConsole.Type;

import org.openstack4j.model.image.Image;
import org.openstack4j.model.image.builder.ImageBuilder;
import org.openstack4j.model.storage.object.SwiftAccount;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.openstack.image.domain.GlanceImage.ImageConcreteBuilder;

public class RSVEStudio implements VEStudio {

	
	String VEID;
	int storageID;
	
	String software;
	String name; //xml文件名
	
	String RSVEID;
	String instanceID;
	String instanceURL;
	String instanceIP;
	String hostname;
    Status instanceSTATUS;
	//认证需要  
	OSClient os;
	
	
	
	//openstack 需要的参数形式
	 String openstackFlavorID, openstackServerName,
	 openstackNetwork, openstackImageID, openstackZone ;
	
	//saltstack 需要的参数
	 String rsenviroment;
	
	
	 //镜像上传部分需要的参数
	 String imageName;
	 String imageId;
	 
	 //判断环境是否部署成功
	 boolean vers ;

	
	
	
	public RSVEStudio(RSVEStudio rsveStudio) {
		
		this.VEID = rsveStudio.VEID;
		this.storageID = rsveStudio.storageID;
		this.instanceID = rsveStudio.instanceID;
		this.instanceURL = rsveStudio.instanceURL;
		this.instanceIP = rsveStudio.instanceIP;
		this.instanceSTATUS = rsveStudio.instanceSTATUS;
		this.os = rsveStudio.os;
		this.software = rsveStudio.software;
		this.openstackFlavorID = rsveStudio.openstackFlavorID;
		this.openstackImageID = rsveStudio.openstackImageID;
		this.openstackNetwork = rsveStudio.openstackNetwork;
		this.openstackServerName = rsveStudio.openstackServerName;
		this.openstackZone = rsveStudio.openstackZone;
		this.rsenviroment = rsveStudio.rsenviroment;
		this.imageName = rsveStudio.imageName;
		this.imageId = rsveStudio.imageId;
		this.hostname = rsveStudio.hostname;
		
		this.vers=rsveStudio.vers;
	
	}

	public RSVEStudio() {
		
	}

	
	
	/*openstack所需要的参数
	 * openstackFlavorID
	 * openstackServerName
	 * openstackNetwork
	 * openstackImageID
	 * openstackZone
	 * */
	
	//解析XML文件 将request收集的参数转化为openstackAPI所需要的参数
	@Override
	public void parsingXMl(VERequest verequest) throws Exception{
	
		//openstackServerName = verequest.getVEName();
		
		String servername=null;
		String cpu = null;
		String storage = null;
		String swap = null;
		String ram = null;
		String imageID = null;
		
		
		String osID=null;
		String commpile=null;
		String pfs=null;
		String database=null;
		String rssoftware=null;
		
		
		//读取xml文档
		SAXReader reader = new SAXReader();
			name = verequest.toXML();
			
			 File file = new File(name);
			Document document = reader.read(file);
			  Element root = document.getRootElement();
			  List<Element> childElements = root.elements();
			  for (Element child : childElements) {
			   //未知属性名情况下
			   /*List<Attribute> attributeList = child.attributes();
			   for (Attribute attr : attributeList) {
			    System.out.println(attr.getName() + ": " + attr.getValue());
			   }*/

			   //已知属性名情况下
			   System.out.println("id: " + child.attributeValue("id"));

			   //未知子元素名情况下
			   /*List<Element> elementList = child.elements();
			   for (Element ele : elementList) {
			    System.out.println(ele.getName() + ": " + ele.getText());
			   }
			   System.out.println();*/

			   //已知子元素名的情况下
			   System.out.println("name:" + child.elementText("name"));
			   servername = child.elementText("name");
			  
			  
			   System.out.println("imageID:" + child.elementText("imageID"));
			   imageID = child.elementText("imageID");
			   
			   
			   System.out.println("cpu:" + child.elementText("cpu"));
			   cpu = child.elementText("cpu");
			   System.out.println("ram:" + child.elementText("ram"));
			   ram = (String)child.elementText("ram");
			   System.out.println("storage:" + child.elementText("storage"));
			   storage = child.elementText("storage");
				
			  
			   osID = child.elementText("osID");
			   commpile = child.elementText("commpile");
			   database = child.elementText("database");
			   pfs = child.elementText("pfs");
			   rssoftware = child.elementText("rssoftware");
				
			System.out.println(osID+commpile+database+pfs+rssoftware);
	
			   
			   
			   
			   //这行是为了格式化美观而存在
			   System.out.println("结束");
			  
			 }
			  
	
	     //--------解析云主机类型部分--------得到flavorID-------（目前暂时解析这三种）

			 
		   if(cpu.equals("2")&&ram.equals("4096")&&storage.equals("40")){
			   openstackFlavorID="3";
			   System.out.println("success3");
		   }
		   else if(cpu.equals("4")&&ram.equals("8192")&&storage.equals("80")){
			   openstackFlavorID="4";
			   System.out.println("success4");
		   }
		   else if(cpu.equals("8")&&ram.equals("16384")&&storage.equals("160")){
			   openstackFlavorID="5";
			   System.out.println("success5");
		   }
		   else System.out.println("所需的云主机类型不存在，需要创建");
			System.out.println("云主机类型部分"+openstackFlavorID);
			
			
			
	/*	//----------基础方法--解析基础镜像的部分------------得到imageID------	
			
			switch (osID) {
			case "centOS6.6":
				openstackImageID="83659e92-b3e3-4339-bebc-815c7a976162";
				break;
			case "ubuntu":
				openstackImageID="27da336e-a500-48aa-872e-465a408eec7c";
				break;
			case "windows":
				openstackImageID="f56ce3db-32de-4e8e-abe8-13d4c84c74e5";
				break;

			default:
				openstackImageID=null;
				break;
			}*/
			
		 System.out.println("得到云主机镜像参数"+openstackImageID);
		 
		 //----------openstack镜像库方法解析镜像-------------
			String imageID1 = null;
			List<? extends Image> images = os.images().list();
			System.out.println(images);
			int max = images.size()-1;
			System.out.println(max);
			for(int i =max ;i >=0;){
			//	System.out.println(i);
				if(images.get(i).getName().equals(osID)){
					 imageID1 = images.get(i).getId();
					System.out.println("得到镜像id"+imageID1);
				}
				i--;
			}
			
			
			if(imageID1==null){
				System.out.println("解析镜像ID失败，镜像仓库中不存在");
				
			}
			else{
				openstackImageID = imageID1;	
			}
		 
		//----------云主机名称------------得到serverName------
		 
		 this.openstackServerName = servername;
		 System.out.println("得到云主机名称参数"+openstackServerName);
		 
		 //----------打印出得到的三个参数------------------------
		 
		 System.out.println("最后参数显示："+openstackServerName+openstackImageID+openstackFlavorID);
		 
		 //-----第四个环境因素 供saltstack使用--------------------
		 
		 this.rsenviroment = os+commpile+database+pfs+rssoftware;
		 
		 software= database+"-"+rssoftware+"-"+pfs;
		 System.out.println("state.sls文件的名称为： "+software);
		 
		 //------如果需要上传镜像，镜像名称命名规则(这里后面需要修改）。。。。。。
		 imageName = osID+"."+database+"."+rssoftware;
		 System.out.println("如果需要上传镜像，镜像名称为"+imageName);

		 System.out.println("软件"+rssoftware);
		 System.out.println("数据库"+database);
		 
	}

	

	//用户认证



	//基于用户名和密码的认证，带两个参数：用户名和密码
	public boolean validation(String username,String password) {
		if(username == "pipsCloud"&&password == "pipsCloudTeam"){
		this.os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials(username, password).tenantName(username)
				.authenticate();
		
		//SwiftAccount account = os.objectStorage().account().get();
		
		System.out.println("完成认证");
		return true;
		}
		else
		return false;
	

	}
	
	
	
	//检查openstack的镜像库
	@Override
	public boolean checkImage() {
		
		/*查询图像 

		// List all Images
		List<? extends Image> images = os.images().list();
			
		// Get an Image by ID
		Image image = os.images().get("imageId");*/

		
		String imageID = null;
		List<? extends Image> images = os.images().list();
		System.out.println(images);
		int max = images.size()-1;
		System.out.println(max);
		for(int i =max ;i >=0;){
		//	System.out.println(i);
			if(images.get(i).getName().equals(imageName)){
				 imageID = images.get(i).getId();
				System.out.println("创建的镜像id"+imageID);
			}
			i--;
		}
		
		
		if(imageID==null){
			return false;
		}
		else{
			openstackImageID = imageID;
			return true;
		}
		

	}

	//建立基本的云主机
	@Override
	public String buildBaseVE() {
		
		//----关于网络部分先采用公用网络测试---
		String openstackNetworkId = "5511543f-3ec1-4b68-8dca-30ed2ebb9b71";//public
		List<String> openStackNetworkList=new ArrayList<String>();
		openStackNetworkList.add(openstackNetworkId);
		String openstackZone = "nova";
		
		ServerCreate sc = Builders.server().name(openstackServerName)
                .flavor(openstackFlavorID)
                .image(openstackImageID)
                .networks(openStackNetworkList)
                .availabilityZone(openstackZone)
                .build();
		//---测试阶段不真正创建--------
		Server server = os.compute().servers().boot(sc);
		System.out.println("创建云主机ID"+server.getId());
		
		//------通过主机名 确定主机的id----instanceID
		List<? extends Server> servers = os.compute().servers().list();
		
		int max = servers.size()-1;
		System.out.println(max);
		for(int i =max ;i >=0;){
			if(servers.get(i).getName().equals(openstackServerName)){
				
				//得到创建实例的全部信息
				Server VEmessage = servers.get(i);
				System.out.println("显示创建的主机的信息："+VEmessage);
				//得到创建的实例的ID
				 instanceID = servers.get(i).getId();
				// servers.get(i).getHost().getBytes();
				System.out.println("创建的实例id"+instanceID);
			
			}
			i--;
		}
		
/*		String nnn=sc.getName();
		 System.out.println("这是什么"+nnn);*/
	
	    System.out.println("云主机的ID为："+instanceID);
	    
	    
			
			for(int i=0;i<=50;i++){
				Server intance = os.compute().servers().get(instanceID);
			     Status status = intance.getStatus();
			     Status ACTIVE = Status.ACTIVE;
			     Status ERROR = Status.ERROR;
				try {
					Thread.sleep(6*1000);
				
					System.out.println(status);
					System.out.println(ACTIVE);
					System.out.println(i+"创建中...");
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				if(status ==ACTIVE){
					System.out.println("创建成功");
	              break;
			     }else if(status ==ERROR){
						System.out.println("创建错误");
			              break;
					     }
			}
			
			//创建的基础实例的网络是 public（目前只有这一个）
			Server intance = os.compute().servers().get(instanceID);
			instanceSTATUS = intance.getStatus();
			if(null != intance.getAddresses().getAddresses("public").get(0).getAddr()){
			instanceIP = intance.getAddresses().getAddresses("public").get(0).getAddr();
			for(int j=0;j<10;j++){
				 hostname = "host-"+instanceIP.replace(".", "-");
				 
			}
			System.out.println("创建的实例的系统名称是："+hostname);
			}else
				System.out.println("网络不是public，系统名称无法确认");
	    
	    VNCConsole console = os.compute().servers().getVNCConsole(instanceID, Type.NOVNC);
	    System.out.println(console);
	    instanceURL = console.getURL();
	    System.out.println("创建的云主机的控制台地址为"+instanceURL);
	    //return instanceURL;
		
			return instanceID;
		
	}
	//确定主机系统名称  instacneID+nework 两个条件
	public String gethostname(String network){
		System.out.println("RSVEStudio.gethostname(network)");
		Server intance = os.compute().servers().get(instanceID);
		instanceSTATUS = intance.getStatus();
		instanceIP = intance.getAddresses().getAddresses(network).get(0).getAddr();
		for(int j=0;j<10;j++){
			 hostname = "host-"+instanceIP.replace(".", "-");
			 
		}
		System.out.println("创建的实例的系统名称是："+hostname);
		
		return hostname;
	}
	//确定主机URL instacneID 两个条件
	public String getURL(String hostID){
		
		VNCConsole console = os.compute().servers().getVNCConsole(hostID, Type.NOVNC);
	    System.out.println(console);
	    instanceURL = console.getURL();
	    System.out.println("创建的云主机的控制台地址为"+instanceURL);
	    return instanceURL;
	}

	//操作控制台节点123 利用saltstack安装需求软件
	//SaltStack认证为主机IP的时候
	@Override
	public void installSoftware() {
		
		String cmd1 = null;
		//------------判断安装的软件-----------
	/*	if(software.equals("httpd")){
		  cmd1 = "slat"+"'+openstackServerName+'"+" state.sls "+"httpd;";
		}else cmd1 = "slat"+"'+openstackServerName+'"+" state.sls "+software+";";	
		*/
		//cmd1 = "salt "+"'"+openstackServerName+"'"+" state.sls "+software+";";
		cmd1 = "salt "+"'"+instanceIP+"'"+" state.sls "+software+"";
		System.out.println(cmd1);
		System.out.println("安装指定的遥感软件:"+software);
		System.out.println("命令执行中1。。。。。");
		SSHLink ss = new SSHLink();
		try {
			Thread.sleep(2*1000);
			System.out.println("命令执行中2。。。。。");
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}	
		
		String cmdresult=ss.command(cmd1);//主节点的安装命令CMD命令
		boolean cm = cmdresult.contains("Succeeded:");
		if(cm == true){
		    System.out.println("安装遥感业务软件成功。");
		    vers = true;
		}else{
			System.out.println("安装遥感业务软件失败");
            vers = false; 		
		}
	}
	
	
	//saltstack认证为主机名称的时候
	public void installSoftware1() {
		
		String cmd1 = null;
		//------------判断安装的软件-----------
	/*	if(software.equals("httpd")){
		  cmd1 = "slat"+"'+openstackServerName+'"+" state.sls "+"httpd;";
		}else cmd1 = "slat"+"'+openstackServerName+'"+" state.sls "+software+";";	
		*/
		//cmd1 = "salt "+"'"+openstackServerName+"'"+" state.sls "+software+";";
		cmd1 = "salt "+"'"+hostname+"'"+" state.sls "+software+"";
		System.out.println(cmd1);
		System.out.println("安装指定的遥感软件:"+software);
		System.out.println("命令执行中1。。。。。");
		SSHLink ss = new SSHLink();
		try {
			Thread.sleep(2*1000);
			System.out.println("命令执行中2。。。。。");
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}	
		String cmdresult=ss.command(cmd1);//主节点的安装命令CMD命令
		boolean cm = cmdresult.contains("Succeeded:");
		if(cm == true){
		    System.out.println("安装遥感业务软件成功。");
		    vers = true;
		}else{
			System.out.println("安装遥感业务软件失败");
            vers = false; 		
		}
	}
	
	public void installSoftware(String host,String user,String psw,int port) {
		
		
		//------------判断安装的软件-----------
		String command = "salt "+"'"+hostname+"'"+" state.sls "+software+"";
		System.out.println(command);
		System.out.println("安装指定的遥感软件:"+software);
		System.out.println("命令执行中1。。。。。");
		SSHLink ss = new SSHLink();
		try {
			Thread.sleep(2*1000);
			System.out.println("命令执行中2。。。。。");
		} catch (InterruptedException e) {	
			e.printStackTrace();
		}	
		//装有salt-master的控制节点执行操作	
		String cmdresult=ss.exec(host, user, psw, port, command);//主节点的安装命令CMD命令
		boolean cm = cmdresult.contains("Succeeded:");
		if(cm == true){
		    System.out.println("安装遥感业务软件成功。");
		    vers = true;
		}else{
			System.out.println("安装遥感业务软件失败");
            vers = false; 		
		}
	}
	
	
	
	//安装软件的指定命令
	public String commd(){
		System.out.println("RSVEStudio.commd()");
		String cmd1 = "salt "+"'"+hostname+"'"+" state.sls "+software+"";
		System.out.println("salt语句为"+cmd1);
		return cmd1;
	}
	
	

	//建立模板云主机以及镜像库中已经存在的镜像系统
	@Override
	public String buildVE() {
				
		//----关于网络部分先采用公用网络测试---
				String openstackNetworkId = "5511543f-3ec1-4b68-8dca-30ed2ebb9b71";//public
				List<String> openStackNetworkList=new ArrayList<String>();
				openStackNetworkList.add(openstackNetworkId);
				String openstackZone = "nova";
				
				ServerCreate sc = Builders.server().name(openstackServerName)
		                .flavor(openstackFlavorID)
		                .image(openstackImageID)
		                .networks(openStackNetworkList)
		                .availabilityZone(openstackZone)
		                .build();

				Server server = os.compute().servers().boot(sc);
				System.out.println("创建云主机ID"+server.getId());
				
				//------通过主机名 确定主机的id----instanceID   ==  server.getId() 
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
						
			    System.out.println("云主机的ID为："+instanceID);
			   				
				for(int i=0;i<=50;i++){
					Server intance = os.compute().servers().get(instanceID);
				     Status status = intance.getStatus();
				     Status ACTIVE = Status.ACTIVE;
				     Status ERROR = Status.ERROR;
					try {
						Thread.sleep(6*1000);
						System.out.println(status);
						System.out.println(ACTIVE);
						System.out.println(i+"创建中...");
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					if(status ==ACTIVE){
						System.out.println("创建成功");
						vers = true;
		              break;
				     }else if(status ==ERROR){
							System.out.println("创建错误");
							vers = false;
				              break;
						     }
				}
			    
			    VNCConsole console = os.compute().servers().getVNCConsole(instanceID, Type.NOVNC);
			    System.out.println(console);
			    instanceURL = console.getURL();
			    System.out.println("创建的云主机的控制台地址为"+instanceURL);
		
		    System.out.println("建立模板云主机"+openstackImageID);
		    //返回的主机的URL
		    return instanceURL;
		

	}

	//创建存储
	@Override
	public String buildStorage() {
		return null;
		
	}
	
	
	
	/*
	 *  许冬 2016/7/27
	 *  修改将os.objectStorage().containers().create("test");
	 *  改为 os.objectStorage().containers().create(StorageName);
	 */
	//创建容器存储 swift
	@Override
	public void buildSwiftStorage(String StorageName){
	
		SwiftAccount account = os.objectStorage().account().get();
		int SwiftNum = (int) account.getContainerCount();
		System.out.println("租户现有容器总数为："+SwiftNum);
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		boolean success = false;
		for(int i = 0; i<SwiftNum; i++){
			if (containers.get(i).getName().equals(StorageName)){
				System.out.println("云盘名称已经存在，请更换名称");
				success = true;	
			}
		}
		if( success == true){
			System.out.println("云盘名称已经存在，请更换名称");
		}else{
		os.objectStorage().containers().create(StorageName);
		System.out.println("创建容器存储"+StorageName+"成功");
		}
		for(int i = 0; i<SwiftNum; i++){
			if (containers.get(i).getName().equals(StorageName)){
				System.out.println("返回容器ID");
				storageID = i;
			}
		}
		
		
	}
	
	public int getStorageID(String StorageName){
		
		SwiftAccount account = os.objectStorage().account().get();
		int SwiftNum = (int) account.getContainerCount();
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		for(int i = 0; i<SwiftNum; i++){
			if (containers.get(i).getName().equals(StorageName)){
				System.out.println("返回容器ID");
				storageID = i;
			}else
				System.out.println("链接云存储"+StorageName+"失败！");
		}
	
		
		return storageID;
	}
	
	//上传文件到swift存储
	public void uploadfile(String StorageName,URL url,String filename){
		System.out.println("RSVEStudio.uploadfile()");
		String etag = os.objectStorage().objects().put(StorageName, filename, Payloads.create(url));
		System.out.println(etag);
	}
	//创建文件夹     ---------还存在问题
	public void createfilepath(String StorageName,String file){
		System.out.println("RSVEStudio.createfilepath()");
		os.objectStorage().containers().createPath(StorageName,file);
		
	}
	
	
	//打包自定义的镜像
	@Override
	public String packageVEImage() {
		
		//打包镜像意思就是创建实例的快照
	//	String imageId = os.compute().servers().createSnapshot(serverId, snapshotName);
		
		 imageId = os.compute().servers().createSnapshot(instanceID, imageName);
		
		//System.out.println("打包镜像"+imageName);
		return null;
		

	}

	//上传镜像到镜像库
	@Override
	public void registerVEImage() {
		
		
		
		//更新一个图像 

      /*  更新一个图像像改变它的名字或其他模板数据见下面的例子。 
*/
		
		this.os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials("admin", "WRognu73").tenantName("admin")
				.authenticate();
		System.out.println("完成管理员认证");
		
		//修改image属性必须是管理员
		
		Image image = os.images().get(imageId);
		ImageBuilder swap = image.toBuilder().name(imageName).isPublic(true);
		Image image1 = swap.build();
		os.images().update(image1);
      /*os.images().update((Image) image.toBuilder()
             .name(imageName).minDisk((long) 1024).property("personal-distro", "true"));
*/
		
		
		System.out.println("上传镜像");

	}
	//测试
	public void checkimage(String imaged, String imageNamed){
		this.os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials("admin", "WRognu73").tenantName("admin")
				.authenticate();
		System.out.println("完成管理员认证");
		
		//修改image属性必须是管理员
		
		Image imag = os.images().get(imaged);
	//	os.images().update(imag);
		System.out.println(imag);
		
		
		ImageBuilder imag1 = imag.toBuilder().name(imageNamed).minDisk((long)1024).isPublic(true).property("personal-distro", "true");
		System.out.println(imag1);
		ImageConcreteBuilder im11 = (ImageConcreteBuilder) imag1;
		System.out.println(im11);
		Image imag2 = (Image) im11.build();
		System.out.println(imag2);
	
	os.images().update(imag2);
	


		
		System.out.println("上传镜像");
	}

	@Override
	public String getVEID() {
		
		this.VEID=instanceID;

		System.out.println("得到云主机ID:"+VEID);
		
		return VEID;
	}
	public String getURL(){
	//	this.instanceURL=instanceURL;
		System.out.println("得到Url地址:"+instanceURL);
		return instanceURL;
	}
	public String getIP(){
	//	this.instanceURL=instanceURL;
		System.out.println("得到IP地址:"+instanceIP);
		return instanceIP;
	}
	public Status getSTATUS(){
		System.out.println("RSVEStudio.getSTATUS()");
	     return instanceSTATUS;
	}

/*	public Server getVEmessage(){
		Server i = os.compute().servers().list().get(0);
		return i;
	}*/
	
	public void test(){
		List<? extends Image> images = os.images().list();
		System.out.println(images);
		int max = images.size()-1;
		System.out.println(max);
		for(int i =max ;i >=0;){
			System.out.println(i);
			if(images.get(i).getName().equals("快照1")){
				String imageID = images.get(i).getId();
				System.out.println("创建的镜像id"+imageID);
			}
			i--;
		}
	}

	@Override
	public String getVEURL() {
		System.out.println("得到Url地址:"+instanceURL);
		return instanceURL;
		
	}
	public boolean deleteVE(){
		System.out.println("RSVEStudio.deleteVE()");
		os.compute().servers().delete(VEID);
		return true;
	}
	public boolean deleteVE(String ID){
		System.out.println("RSVEStudio.deleteVE(String ID)");
		os.compute().servers().delete(ID);
		return true;
	}

	public boolean isVers() {
		return vers;
	}

	public void setVers(boolean vers) {
		this.vers = vers;
	}

}
