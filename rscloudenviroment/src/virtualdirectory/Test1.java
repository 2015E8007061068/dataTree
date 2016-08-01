package virtualdirectory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.LogManager;

import rscloudenviroment.StudioFactory;
import rscloudenviroment.VEStudio;

public class Test1 {



	/*
	 * 输出容器container的原数据
	 */
	public static void printMetaData(Map<String, String> metaData) {

		String key = null;
		String value = null;
		Iterator iter = metaData.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			key = (String) entry.getKey();
			value = (String) entry.getValue();
			System.out.println("元数据的key为：" + key + " 元数据的value为：" + value);

		}
	}
	
	 

	public static void main(String[] args) {
		
		// VEStudio rSVeStudio = StudioFactory.creator("RSVE");

		// 后面需要改成工厂方法
		 RSVEStudioChild rSVeStudio = new RSVEStudioChild();

		boolean er = rSVeStudio.validation("pipsCloud", "pipsCloudTeam");
		if (er == true) {
			System.out.println("=====进入openstack成功====");
		} else {
			System.out.println("=====进入openstack失败====");
			return;
		}

		/*
		 * to-do 创建一个新容器 xdtest
		 */

		 //rSVeStudio.buildSwiftStorage("xdcopy1");

		// 取出容器的原数据
		Map<String, String> metaData = new HashMap<String, String>();
		metaData = rSVeStudio.getMetadata("xd");
		printMetaData(metaData);

		// 创建容器下面的虚拟目录
		//LogManager.getLogManager().reset();
		//rSVeStudio.createfilepath("xd", "虚拟目录文件","/home/"+"beijing");
		
		//从一个容器拷贝到另外一个容器
		rSVeStudio.copyObjectDuringContainers("home", "HJ1/sensor1/beijing/20150707/faaa", "xd", "sss","/home");
	}

}
