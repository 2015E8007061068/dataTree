
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
	String name; //xml�ļ���
	
	String RSVEID;
	String instanceID;
	String instanceURL;
	String instanceIP;
	String hostname;
    Status instanceSTATUS;
	//��֤��Ҫ  
	OSClient os;
	
	
	
	//openstack ��Ҫ�Ĳ�����ʽ
	 String openstackFlavorID, openstackServerName,
	 openstackNetwork, openstackImageID, openstackZone ;
	
	//saltstack ��Ҫ�Ĳ���
	 String rsenviroment;
	
	
	 //�����ϴ�������Ҫ�Ĳ���
	 String imageName;
	 String imageId;
	 
	 //�жϻ����Ƿ���ɹ�
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

	
	
	/*openstack����Ҫ�Ĳ���
	 * openstackFlavorID
	 * openstackServerName
	 * openstackNetwork
	 * openstackImageID
	 * openstackZone
	 * */
	
	//����XML�ļ� ��request�ռ��Ĳ���ת��ΪopenstackAPI����Ҫ�Ĳ���
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
		
		
		//��ȡxml�ĵ�
		SAXReader reader = new SAXReader();
			name = verequest.toXML();
			
			 File file = new File(name);
			Document document = reader.read(file);
			  Element root = document.getRootElement();
			  List<Element> childElements = root.elements();
			  for (Element child : childElements) {
			   //δ֪�����������
			   /*List<Attribute> attributeList = child.attributes();
			   for (Attribute attr : attributeList) {
			    System.out.println(attr.getName() + ": " + attr.getValue());
			   }*/

			   //��֪�����������
			   System.out.println("id: " + child.attributeValue("id"));

			   //δ֪��Ԫ���������
			   /*List<Element> elementList = child.elements();
			   for (Element ele : elementList) {
			    System.out.println(ele.getName() + ": " + ele.getText());
			   }
			   System.out.println();*/

			   //��֪��Ԫ�����������
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
	
			   
			   
			   
			   //������Ϊ�˸�ʽ�����۶�����
			   System.out.println("����");
			  
			 }
			  
	
	     //--------�������������Ͳ���--------�õ�flavorID-------��Ŀǰ��ʱ���������֣�

			 
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
		   else System.out.println("��������������Ͳ����ڣ���Ҫ����");
			System.out.println("���������Ͳ���"+openstackFlavorID);
			
			
			
	/*	//----------��������--������������Ĳ���------------�õ�imageID------	
			
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
			
		 System.out.println("�õ��������������"+openstackImageID);
		 
		 //----------openstack����ⷽ����������-------------
			String imageID1 = null;
			List<? extends Image> images = os.images().list();
			System.out.println(images);
			int max = images.size()-1;
			System.out.println(max);
			for(int i =max ;i >=0;){
			//	System.out.println(i);
				if(images.get(i).getName().equals(osID)){
					 imageID1 = images.get(i).getId();
					System.out.println("�õ�����id"+imageID1);
				}
				i--;
			}
			
			
			if(imageID1==null){
				System.out.println("��������IDʧ�ܣ�����ֿ��в�����");
				
			}
			else{
				openstackImageID = imageID1;	
			}
		 
		//----------����������------------�õ�serverName------
		 
		 this.openstackServerName = servername;
		 System.out.println("�õ����������Ʋ���"+openstackServerName);
		 
		 //----------��ӡ���õ�����������------------------------
		 
		 System.out.println("��������ʾ��"+openstackServerName+openstackImageID+openstackFlavorID);
		 
		 //-----���ĸ��������� ��saltstackʹ��--------------------
		 
		 this.rsenviroment = os+commpile+database+pfs+rssoftware;
		 
		 software= database+"-"+rssoftware+"-"+pfs;
		 System.out.println("state.sls�ļ�������Ϊ�� "+software);
		 
		 //------�����Ҫ�ϴ����񣬾���������������(���������Ҫ�޸ģ�������������
		 imageName = osID+"."+database+"."+rssoftware;
		 System.out.println("�����Ҫ�ϴ����񣬾�������Ϊ"+imageName);

		 System.out.println("���"+rssoftware);
		 System.out.println("���ݿ�"+database);
		 
	}

	

	//�û���֤



	//�����û������������֤���������������û���������
	public boolean validation(String username,String password) {
		if(username == "pipsCloud"&&password == "pipsCloudTeam"){
		this.os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials(username, password).tenantName(username)
				.authenticate();
		
		//SwiftAccount account = os.objectStorage().account().get();
		
		System.out.println("�����֤");
		return true;
		}
		else
		return false;
	

	}
	
	
	
	//���openstack�ľ����
	@Override
	public boolean checkImage() {
		
		/*��ѯͼ�� 

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
				System.out.println("�����ľ���id"+imageID);
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

	//����������������
	@Override
	public String buildBaseVE() {
		
		//----�������粿���Ȳ��ù����������---
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
		//---���Խ׶β���������--------
		Server server = os.compute().servers().boot(sc);
		System.out.println("����������ID"+server.getId());
		
		//------ͨ�������� ȷ��������id----instanceID
		List<? extends Server> servers = os.compute().servers().list();
		
		int max = servers.size()-1;
		System.out.println(max);
		for(int i =max ;i >=0;){
			if(servers.get(i).getName().equals(openstackServerName)){
				
				//�õ�����ʵ����ȫ����Ϣ
				Server VEmessage = servers.get(i);
				System.out.println("��ʾ��������������Ϣ��"+VEmessage);
				//�õ�������ʵ����ID
				 instanceID = servers.get(i).getId();
				// servers.get(i).getHost().getBytes();
				System.out.println("������ʵ��id"+instanceID);
			
			}
			i--;
		}
		
/*		String nnn=sc.getName();
		 System.out.println("����ʲô"+nnn);*/
	
	    System.out.println("��������IDΪ��"+instanceID);
	    
	    
			
			for(int i=0;i<=50;i++){
				Server intance = os.compute().servers().get(instanceID);
			     Status status = intance.getStatus();
			     Status ACTIVE = Status.ACTIVE;
			     Status ERROR = Status.ERROR;
				try {
					Thread.sleep(6*1000);
				
					System.out.println(status);
					System.out.println(ACTIVE);
					System.out.println(i+"������...");
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				if(status ==ACTIVE){
					System.out.println("�����ɹ�");
	              break;
			     }else if(status ==ERROR){
						System.out.println("��������");
			              break;
					     }
			}
			
			//�����Ļ���ʵ���������� public��Ŀǰֻ����һ����
			Server intance = os.compute().servers().get(instanceID);
			instanceSTATUS = intance.getStatus();
			if(null != intance.getAddresses().getAddresses("public").get(0).getAddr()){
			instanceIP = intance.getAddresses().getAddresses("public").get(0).getAddr();
			for(int j=0;j<10;j++){
				 hostname = "host-"+instanceIP.replace(".", "-");
				 
			}
			System.out.println("������ʵ����ϵͳ�����ǣ�"+hostname);
			}else
				System.out.println("���粻��public��ϵͳ�����޷�ȷ��");
	    
	    VNCConsole console = os.compute().servers().getVNCConsole(instanceID, Type.NOVNC);
	    System.out.println(console);
	    instanceURL = console.getURL();
	    System.out.println("�������������Ŀ���̨��ַΪ"+instanceURL);
	    //return instanceURL;
		
			return instanceID;
		
	}
	//ȷ������ϵͳ����  instacneID+nework ��������
	public String gethostname(String network){
		System.out.println("RSVEStudio.gethostname(network)");
		Server intance = os.compute().servers().get(instanceID);
		instanceSTATUS = intance.getStatus();
		instanceIP = intance.getAddresses().getAddresses(network).get(0).getAddr();
		for(int j=0;j<10;j++){
			 hostname = "host-"+instanceIP.replace(".", "-");
			 
		}
		System.out.println("������ʵ����ϵͳ�����ǣ�"+hostname);
		
		return hostname;
	}
	//ȷ������URL instacneID ��������
	public String getURL(String hostID){
		
		VNCConsole console = os.compute().servers().getVNCConsole(hostID, Type.NOVNC);
	    System.out.println(console);
	    instanceURL = console.getURL();
	    System.out.println("�������������Ŀ���̨��ַΪ"+instanceURL);
	    return instanceURL;
	}

	//��������̨�ڵ�123 ����saltstack��װ�������
	//SaltStack��֤Ϊ����IP��ʱ��
	@Override
	public void installSoftware() {
		
		String cmd1 = null;
		//------------�жϰ�װ�����-----------
	/*	if(software.equals("httpd")){
		  cmd1 = "slat"+"'+openstackServerName+'"+" state.sls "+"httpd;";
		}else cmd1 = "slat"+"'+openstackServerName+'"+" state.sls "+software+";";	
		*/
		//cmd1 = "salt "+"'"+openstackServerName+"'"+" state.sls "+software+";";
		cmd1 = "salt "+"'"+instanceIP+"'"+" state.sls "+software+"";
		System.out.println(cmd1);
		System.out.println("��װָ����ң�����:"+software);
		System.out.println("����ִ����1����������");
		SSHLink ss = new SSHLink();
		try {
			Thread.sleep(2*1000);
			System.out.println("����ִ����2����������");
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}	
		
		String cmdresult=ss.command(cmd1);//���ڵ�İ�װ����CMD����
		boolean cm = cmdresult.contains("Succeeded:");
		if(cm == true){
		    System.out.println("��װң��ҵ������ɹ���");
		    vers = true;
		}else{
			System.out.println("��װң��ҵ�����ʧ��");
            vers = false; 		
		}
	}
	
	
	//saltstack��֤Ϊ�������Ƶ�ʱ��
	public void installSoftware1() {
		
		String cmd1 = null;
		//------------�жϰ�װ�����-----------
	/*	if(software.equals("httpd")){
		  cmd1 = "slat"+"'+openstackServerName+'"+" state.sls "+"httpd;";
		}else cmd1 = "slat"+"'+openstackServerName+'"+" state.sls "+software+";";	
		*/
		//cmd1 = "salt "+"'"+openstackServerName+"'"+" state.sls "+software+";";
		cmd1 = "salt "+"'"+hostname+"'"+" state.sls "+software+"";
		System.out.println(cmd1);
		System.out.println("��װָ����ң�����:"+software);
		System.out.println("����ִ����1����������");
		SSHLink ss = new SSHLink();
		try {
			Thread.sleep(2*1000);
			System.out.println("����ִ����2����������");
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}	
		String cmdresult=ss.command(cmd1);//���ڵ�İ�װ����CMD����
		boolean cm = cmdresult.contains("Succeeded:");
		if(cm == true){
		    System.out.println("��װң��ҵ������ɹ���");
		    vers = true;
		}else{
			System.out.println("��װң��ҵ�����ʧ��");
            vers = false; 		
		}
	}
	
	public void installSoftware(String host,String user,String psw,int port) {
		
		
		//------------�жϰ�װ�����-----------
		String command = "salt "+"'"+hostname+"'"+" state.sls "+software+"";
		System.out.println(command);
		System.out.println("��װָ����ң�����:"+software);
		System.out.println("����ִ����1����������");
		SSHLink ss = new SSHLink();
		try {
			Thread.sleep(2*1000);
			System.out.println("����ִ����2����������");
		} catch (InterruptedException e) {	
			e.printStackTrace();
		}	
		//װ��salt-master�Ŀ��ƽڵ�ִ�в���	
		String cmdresult=ss.exec(host, user, psw, port, command);//���ڵ�İ�װ����CMD����
		boolean cm = cmdresult.contains("Succeeded:");
		if(cm == true){
		    System.out.println("��װң��ҵ������ɹ���");
		    vers = true;
		}else{
			System.out.println("��װң��ҵ�����ʧ��");
            vers = false; 		
		}
	}
	
	
	
	//��װ�����ָ������
	public String commd(){
		System.out.println("RSVEStudio.commd()");
		String cmd1 = "salt "+"'"+hostname+"'"+" state.sls "+software+"";
		System.out.println("salt���Ϊ"+cmd1);
		return cmd1;
	}
	
	

	//����ģ���������Լ���������Ѿ����ڵľ���ϵͳ
	@Override
	public String buildVE() {
				
		//----�������粿���Ȳ��ù����������---
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
				System.out.println("����������ID"+server.getId());
				
				//------ͨ�������� ȷ��������id----instanceID   ==  server.getId() 
				List<? extends Server> servers = os.compute().servers().list();
				int max = servers.size()-1;
				System.out.println(max);
				for(int i =max ;i >=0;){
					if(servers.get(i).getName().equals(openstackServerName)){
						 instanceID = servers.get(i).getId();
						System.out.println("������ʵ��id"+instanceID);
					}
					i--;
				}
						
			    System.out.println("��������IDΪ��"+instanceID);
			   				
				for(int i=0;i<=50;i++){
					Server intance = os.compute().servers().get(instanceID);
				     Status status = intance.getStatus();
				     Status ACTIVE = Status.ACTIVE;
				     Status ERROR = Status.ERROR;
					try {
						Thread.sleep(6*1000);
						System.out.println(status);
						System.out.println(ACTIVE);
						System.out.println(i+"������...");
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					if(status ==ACTIVE){
						System.out.println("�����ɹ�");
						vers = true;
		              break;
				     }else if(status ==ERROR){
							System.out.println("��������");
							vers = false;
				              break;
						     }
				}
			    
			    VNCConsole console = os.compute().servers().getVNCConsole(instanceID, Type.NOVNC);
			    System.out.println(console);
			    instanceURL = console.getURL();
			    System.out.println("�������������Ŀ���̨��ַΪ"+instanceURL);
		
		    System.out.println("����ģ��������"+openstackImageID);
		    //���ص�������URL
		    return instanceURL;
		

	}

	//�����洢
	@Override
	public String buildStorage() {
		return null;
		
	}
	
	
	
	/*
	 *  �� 2016/7/27
	 *  �޸Ľ�os.objectStorage().containers().create("test");
	 *  ��Ϊ os.objectStorage().containers().create(StorageName);
	 */
	//���������洢 swift
	@Override
	public void buildSwiftStorage(String StorageName){
	
		SwiftAccount account = os.objectStorage().account().get();
		int SwiftNum = (int) account.getContainerCount();
		System.out.println("�⻧������������Ϊ��"+SwiftNum);
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		boolean success = false;
		for(int i = 0; i<SwiftNum; i++){
			if (containers.get(i).getName().equals(StorageName)){
				System.out.println("���������Ѿ����ڣ����������");
				success = true;	
			}
		}
		if( success == true){
			System.out.println("���������Ѿ����ڣ����������");
		}else{
		os.objectStorage().containers().create(StorageName);
		System.out.println("���������洢"+StorageName+"�ɹ�");
		}
		for(int i = 0; i<SwiftNum; i++){
			if (containers.get(i).getName().equals(StorageName)){
				System.out.println("��������ID");
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
				System.out.println("��������ID");
				storageID = i;
			}else
				System.out.println("�����ƴ洢"+StorageName+"ʧ�ܣ�");
		}
	
		
		return storageID;
	}
	
	//�ϴ��ļ���swift�洢
	public void uploadfile(String StorageName,URL url,String filename){
		System.out.println("RSVEStudio.uploadfile()");
		String etag = os.objectStorage().objects().put(StorageName, filename, Payloads.create(url));
		System.out.println(etag);
	}
	//�����ļ���     ---------����������
	public void createfilepath(String StorageName,String file){
		System.out.println("RSVEStudio.createfilepath()");
		os.objectStorage().containers().createPath(StorageName,file);
		
	}
	
	
	//����Զ���ľ���
	@Override
	public String packageVEImage() {
		
		//���������˼���Ǵ���ʵ���Ŀ���
	//	String imageId = os.compute().servers().createSnapshot(serverId, snapshotName);
		
		 imageId = os.compute().servers().createSnapshot(instanceID, imageName);
		
		//System.out.println("�������"+imageName);
		return null;
		

	}

	//�ϴ����񵽾����
	@Override
	public void registerVEImage() {
		
		
		
		//����һ��ͼ�� 

      /*  ����һ��ͼ����ı��������ֻ�����ģ�����ݼ���������ӡ� 
*/
		
		this.os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials("admin", "WRognu73").tenantName("admin")
				.authenticate();
		System.out.println("��ɹ���Ա��֤");
		
		//�޸�image���Ա����ǹ���Ա
		
		Image image = os.images().get(imageId);
		ImageBuilder swap = image.toBuilder().name(imageName).isPublic(true);
		Image image1 = swap.build();
		os.images().update(image1);
      /*os.images().update((Image) image.toBuilder()
             .name(imageName).minDisk((long) 1024).property("personal-distro", "true"));
*/
		
		
		System.out.println("�ϴ�����");

	}
	//����
	public void checkimage(String imaged, String imageNamed){
		this.os = OSFactory.builder()
				.endpoint("http://10.3.1.123:5000/v2.0")
				.credentials("admin", "WRognu73").tenantName("admin")
				.authenticate();
		System.out.println("��ɹ���Ա��֤");
		
		//�޸�image���Ա����ǹ���Ա
		
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
	


		
		System.out.println("�ϴ�����");
	}

	@Override
	public String getVEID() {
		
		this.VEID=instanceID;

		System.out.println("�õ�������ID:"+VEID);
		
		return VEID;
	}
	public String getURL(){
	//	this.instanceURL=instanceURL;
		System.out.println("�õ�Url��ַ:"+instanceURL);
		return instanceURL;
	}
	public String getIP(){
	//	this.instanceURL=instanceURL;
		System.out.println("�õ�IP��ַ:"+instanceIP);
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
			if(images.get(i).getName().equals("����1")){
				String imageID = images.get(i).getId();
				System.out.println("�����ľ���id"+imageID);
			}
			i--;
		}
	}

	@Override
	public String getVEURL() {
		System.out.println("�õ�Url��ַ:"+instanceURL);
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
