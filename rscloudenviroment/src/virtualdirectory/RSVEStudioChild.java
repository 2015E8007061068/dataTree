package virtualdirectory;

import static org.openstack4j.core.transport.ClientConstants.CONTENT_TYPE_DIRECTORY;
import static org.openstack4j.model.storage.object.SwiftHeaders.CONTENT_LENGTH;
import static org.openstack4j.model.storage.object.SwiftHeaders.X_COPY_FROM;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.Apis;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.storage.ObjectStorageObjectService;
import org.openstack4j.core.transport.HttpResponse;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.model.storage.object.SwiftAccount;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.model.storage.object.options.ObjectLocation;
import org.openstack4j.model.storage.object.options.ObjectPutOptions;
import org.openstack4j.openstack.OSFactory;

/*
 * <p>Title: </p>
 * <p>Description: 继承RSVEStudio，便于修改函数功能</p>
 * @author  xudong
 * @date 2016/7/27
 */

import rscloudenviroment.RSVEStudio;

public class RSVEStudioChild extends RSVEStudio {

	String VEID;
	int storageID;

	String software;
	String name; // xml文件名

	String RSVEID;
	String instanceID;
	String instanceURL;
	String instanceIP;
	String hostname;
	Status instanceSTATUS;
	// 认证需要
	OSClient os;

	// openstack 需要的参数形式
	String openstackFlavorID, openstackServerName, openstackNetwork, openstackImageID, openstackZone;

	// saltstack 需要的参数
	String rsenviroment;

	// 镜像上传部分需要的参数
	String imageName;
	String imageId;

	// 判断环境是否部署成功
	boolean vers;

	public RSVEStudioChild() {

		super();
	}

	// 基于用户名和密码的认证，带两个参数：用户名和密码
	public boolean validation(String username, String password) {
		System.out.println("RSVEStudioChild.validation(String username,String password)");

		if (username == "pipsCloud" && password == "pipsCloudTeam") {
			this.os = OSFactory.builder().endpoint("http://10.3.1.123:5000/v2.0").credentials(username, password)
					.tenantName(username).authenticate();

			// SwiftAccount account = os.objectStorage().account().get();

			System.out.println("完成认证");
			return true;
		} else
			return false;

	}

	/*
	 * 取出容器的原数据
	 */
	public Map<String, String> getMetadata(String StorageName) {
		System.out.println("RSVEStudioChild.getMetadata(String StorageName)");
		SwiftAccount account = os.objectStorage().account().get();

		Map<String, String> metaData = new HashMap<String, String>();

		boolean isExitst = false;
		// 非空判断
		int SwiftNum = (int) account.getContainerCount();
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		for (int i = 0; i < SwiftNum; i++) {
			if (containers.get(i).getName().equals(StorageName)) {

				System.out.println("查到了该容器，容器ID为" + i);
				isExitst = true;
			}
		}

		if (isExitst == false) {
			System.out.println("该容器不存在，请检查输入名");
		}

		metaData = os.objectStorage().containers().getMetadata(StorageName);

		return metaData;

	}

	/*
	 * 创建swift虚拟目录(文件夹) 
	 */
	public void createfilepath(String containerName, String objectName,String filePath) {
		System.out.println("RSVEStudio.createfilepath()");
		//os.objectStorage().containers().createPath(StorageName, filePath);
		String fileName="D:"+File.separator+"test.txt";
		File file = new File(fileName);
		Apis.get(ObjectStorageObjectService.class).put(containerName, objectName, 
				Payloads.create(file),ObjectPutOptions.create().path(filePath));

	}

	/*
	 * 由一个容器向另一个容器拷贝对象
	 */
	public boolean copyObjectDuringContainers(String srContainerName,String srObjectName,
			String desContainerName,String desObjectName,String url){
		System.out.println("RSVEStudioChild.copyObjectDuringContainers()");
		SwiftAccount account = os.objectStorage().account().get();
		
		ObjectLocation srcLocation = ObjectLocation.create(srContainerName, srObjectName);
		ObjectLocation destLocation = ObjectLocation.create(desContainerName, desObjectName);
		
		
		// 源文件非空判断    当数据量特别大的时候这里的效率可能会比较低？
		boolean isExitst = false;
		int SwiftNum = (int) account.getContainerCount();
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		for (int i = 0; i < SwiftNum; i++) {
			if (containers.get(i).getName().equals(srcLocation.getContainerName())) {

				System.out.println("查到了该容器，容器ID为" + i);
				int objectNumber = containers.get(i).getObjectCount();
				List<? extends SwiftObject> objects = os.objectStorage().objects().list(srContainerName);
				for(int j = 0; j < objectNumber; j++){
					//System.out.println(objects.get(j).getName());
					if(objects.get(j).getName().equals(srObjectName))
						System.out.println("查到了该对象，对象ID为" + j);
						isExitst = true;
				}
				
			}
		}

		if (isExitst == false) {
			System.out.println("该容器或对象不存在，请检查输入名");
		}
		
		// 目标文件存在判断
		boolean isExitst1 = true;
		for (int i = 0; i < SwiftNum; i++) {
			if (containers.get(i).getName().equals(destLocation.getContainerName())) {

				System.out.println("查到了该容器，容器ID为" + i);
				int objectNumber = containers.get(i).getObjectCount();
				List<? extends SwiftObject> objects = os.objectStorage().objects().list(desContainerName);
				for(int j = 0; j < objectNumber; j++){
					if(objects.get(j).getName().equals(srObjectName))
						System.out.println("该对象在目标容器中已存在，对象ID为" + j+"请重新定义一个对象名称");
						isExitst1 = false;
						}
				}
		}

		// copy 操作
		if((isExitst==true)&&(isExitst1==false)){
			System.out.println("该对象在目标容器中不存在");
			System.out.println("原地址url"+srcLocation.getURI()+"目的地址url"+destLocation.getURI());
//			HttpResponse resp = put(Void.class, destLocation.getURI())
//                    .header(X_COPY_FROM, srcLocation.getURI())
//                    .header(CONTENT_LENGTH, 0)
//                    .executeWithResponse();
			//System.out.println(os.objectStorage().objects().copy(srcLocation, destLocation));
			
			DLPayload download = os.objectStorage().objects().download(srContainerName, srObjectName);
			os.objectStorage().objects().put(desContainerName, desObjectName, 
					Payloads.create(download.getInputStream()),ObjectPutOptions.create().path(url));
			
		}
		return true;
	}

}
