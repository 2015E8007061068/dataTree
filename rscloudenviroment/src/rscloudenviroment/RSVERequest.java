package rscloudenviroment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class RSVERequest implements VERequest {

	public String VEName;
	public String VEImage;
	public String SoftWare;
	
	//云主机类型的名称
	public String VEtypename;
	//云主机类型id
	public int VEtypeid;
	//是否公有
	public boolean isPublic;
	
	
	

    //。。。。。。。。。。。。计算资源部分。。。。。。。。。。。
	//云主机的内核数
	public String Vcpu;
	//内存大小
	public String Ram;
	//云主机硬盘大小
	public String Disk;
	//云主机交换盘大小
	public String Swap;
	/*//输入/输出功率
	public String RxtxVEtype;*/
	
	//。。。。。。。。。。。。。服务资源部分。。。。。。。。。。。。。。
	//OS操作系统选择-------------opestack镜像的名称
	
	public String OS;
	//编译器
	public String Commpile;
	//数据库
	public String Database;
	//并行文件系统Parallel File System
	public String PFS;
	//遥感相关软件
	public String RSsoftware;
	
	
	
	
	
	public String getVcpu() {
		System.out.println("RSVERequest.getVcpu()");
		return Vcpu;
	}


	public void setVcpu(String vcpu) {
		System.out.println("RSVERequest.setVcpu()");
		Vcpu = vcpu;
	}


	public String getDisk() {
		System.out.println("RSVERequest.getDisk()");
		return Disk;
	}


	public void setDisk(String disk) {
		System.out.println("RSVERequest.setDisk()");
		Disk = disk;
	}


	public String getSwap() {
		System.out.println("RSVERequest.getSwap()");
		return Swap;
	}


	public void setSwap(String swap) {
		System.out.println("RSVERequest.setSwap()");
		this.Swap = swap;
	}


	public String getRam() {
		System.out.println("RSVERequest.getRam()");
		return Ram;
	}


	public void setRam(String ram) {
		System.out.println("RSVERequest.setRam()");
		this.Ram = ram;
	}
	
	
	public String getOS() {
		System.out.println("RSVERequest.getOS()");
		return OS;
	}


	public void setOS(String oS) {
		System.out.println("RSVERequest.setOS()");
		OS = oS;
	}


	public String getCommpile() {
		System.out.println("RSVERequest.getCommpile()");
		return Commpile;
	}


	public void setCommpile(String commpile) {
		System.out.println("RSVERequest.setCommpile()");
		Commpile = commpile;
	}


	public String getDatabase() {
		System.out.println("RSVERequest.getDatabase()");
		return Database;
	}


	public void setDatabase(String database) {
		System.out.println("RSVERequest.setDatabase()");
		Database = database;
	}


	public String getPFS() {
		System.out.println("RSVERequest.getPFS()");
		return PFS;
	}


	public void setPFS(String pFS) {
		System.out.println("RSVERequest.setPFS()");
		PFS = pFS;
	}


	public String getRSsoftware() {
		System.out.println("RSVERequest.getRSsoftware()");
		return RSsoftware;
	}


	public void setRSsoftware(String rSsoftware) {
		System.out.println("RSVERequest.setRSsoftware()");
		RSsoftware = rSsoftware;
	}


	//设置云主机的主机名
	@Override
	public void setVEName(String VEName) {
		System.out.println("RSVERequest.setVEName()");
		this.VEName = VEName;
	}

	
	@Override
	public String getVEName() {
		System.out.println("RSVERequest.getVEName()");
		return VEName;
		
	}

	//云主机要使用的系统 Linux  、 Windows 或者其它
	@Override
	public void setVEImage(String VEImage) {
		
		
	/*	 System.out.print("请输入一个字符型VEImage：");
		  BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
		  try {
			VEImage=strin.readLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}*/
		System.out.println("RSVERequest.setVEImage()");
		this.VEImage = VEImage;
	
	}

	@Override
	public String getVEImage() {
		System.out.println("RSVERequest.getVEImage()");
		return VEImage;
	}
	
	//云主机定制遥感软件（以后补充相应参数）
	public String getSoftWare() {
		System.out.println("RSVERequest.getSoftWare()");
		return SoftWare;
	}

	public void setSoftWare(String softWare) {
		System.out.println("RSVERequest.setSoftWare()");
		this.SoftWare = softWare;
	}
	
	@Override
	public String toXML() throws IOException{
		
		
		System.out.println("RSVERequest.toXML()");
		
		Document doc = DocumentHelper.createDocument();
		  //增加根节点
		  Element books = doc.addElement("servers");
		  //增加子元素
	
		  Element a = books.addElement("server");
		  Element title1 = a.addElement("name");
		  Element author1 = a.addElement("imageID");
	
		  Element a1 = a.addElement("cpu");
		  Element a2 = a.addElement("storage");
		  Element a3 = a.addElement("swap");
		  Element a4 = a.addElement("ram");
		  
		  Element b1 = a.addElement("osID");
		  Element b2 = a.addElement("commpile");
		  Element b3 = a.addElement("database");
		  Element b4 = a.addElement("pfs");
		  Element b5 = a.addElement("rssoftware");
		  
		  

		  
		  //为子节点添加属性
	//	  book1.addAttribute("id", "1");
		  //为元素添加内容
		  title1.setText(VEName);
		  author1.setText(VEImage);
		  
		  
		  a.addAttribute("id", "1");
		  a1.setText(Vcpu);
		  a2.setText(Disk);
		  a3.setText(Swap);
		  a4.setText(Ram);
		  
		  b1.setText(OS);
		  b2.setText(Commpile);
		  b3.setText(Database);
		  b4.setText(PFS);
		  b5.setText(RSsoftware);
		  
		  
		  //实例化输出格式对象
		  OutputFormat format = OutputFormat.createPrettyPrint();
		  //设置输出编码
		  format.setEncoding("UTF-8");
		  
		  
		/*  File file = new File("src"+File.separator +"request.xml");
		  String name = "src"+File.separator +"request.xml";*/
		  File file = new File("request.xml");
		  String name ="request.xml";
		  System.out.println(name);
		  XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
		
		  writer.write(doc);
		
		
		
		
		return name;
		
		
		
	}

	//拷贝构造函数
	public RSVERequest(RSVERequest rsverequsest){
		System.out.println("RSVERequest.RSVERequest(RSVERequest rsverequsest)");
		this.VEName = rsverequsest.VEName;
		this.VEImage = rsverequsest.VEImage;
		this.Vcpu = rsverequsest.Vcpu;
		this.Disk = rsverequsest.Disk;
		this.Swap = rsverequsest.Swap;
		this.Ram = rsverequsest.Ram;
		this.OS = rsverequsest.OS;
		this.Commpile = rsverequsest.Commpile;
		this.Database = rsverequsest.Database;
		this.PFS = rsverequsest.PFS;
		this.RSsoftware = rsverequsest.RSsoftware;
			
	}

	public RSVERequest() {
		System.out.println("RSVERequest.RSVERequest()");
		
	}



	


}
