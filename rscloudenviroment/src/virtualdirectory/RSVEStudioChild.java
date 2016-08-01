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
 * <p>Description: �̳�RSVEStudio�������޸ĺ�������</p>
 * @author  xudong
 * @date 2016/7/27
 */

import rscloudenviroment.RSVEStudio;

public class RSVEStudioChild extends RSVEStudio {

	String VEID;
	int storageID;

	String software;
	String name; // xml�ļ���

	String RSVEID;
	String instanceID;
	String instanceURL;
	String instanceIP;
	String hostname;
	Status instanceSTATUS;
	// ��֤��Ҫ
	OSClient os;

	// openstack ��Ҫ�Ĳ�����ʽ
	String openstackFlavorID, openstackServerName, openstackNetwork, openstackImageID, openstackZone;

	// saltstack ��Ҫ�Ĳ���
	String rsenviroment;

	// �����ϴ�������Ҫ�Ĳ���
	String imageName;
	String imageId;

	// �жϻ����Ƿ���ɹ�
	boolean vers;

	public RSVEStudioChild() {

		super();
	}

	// �����û������������֤���������������û���������
	public boolean validation(String username, String password) {
		System.out.println("RSVEStudioChild.validation(String username,String password)");

		if (username == "pipsCloud" && password == "pipsCloudTeam") {
			this.os = OSFactory.builder().endpoint("http://10.3.1.123:5000/v2.0").credentials(username, password)
					.tenantName(username).authenticate();

			// SwiftAccount account = os.objectStorage().account().get();

			System.out.println("�����֤");
			return true;
		} else
			return false;

	}

	/*
	 * ȡ��������ԭ����
	 */
	public Map<String, String> getMetadata(String StorageName) {
		System.out.println("RSVEStudioChild.getMetadata(String StorageName)");
		SwiftAccount account = os.objectStorage().account().get();

		Map<String, String> metaData = new HashMap<String, String>();

		boolean isExitst = false;
		// �ǿ��ж�
		int SwiftNum = (int) account.getContainerCount();
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		for (int i = 0; i < SwiftNum; i++) {
			if (containers.get(i).getName().equals(StorageName)) {

				System.out.println("�鵽�˸�����������IDΪ" + i);
				isExitst = true;
			}
		}

		if (isExitst == false) {
			System.out.println("�����������ڣ�����������");
		}

		metaData = os.objectStorage().containers().getMetadata(StorageName);

		return metaData;

	}

	/*
	 * ����swift����Ŀ¼(�ļ���) 
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
	 * ��һ����������һ��������������
	 */
	public boolean copyObjectDuringContainers(String srContainerName,String srObjectName,
			String desContainerName,String desObjectName,String url){
		System.out.println("RSVEStudioChild.copyObjectDuringContainers()");
		SwiftAccount account = os.objectStorage().account().get();
		
		ObjectLocation srcLocation = ObjectLocation.create(srContainerName, srObjectName);
		ObjectLocation destLocation = ObjectLocation.create(desContainerName, desObjectName);
		
		
		// Դ�ļ��ǿ��ж�    ���������ر���ʱ�������Ч�ʿ��ܻ�Ƚϵͣ�
		boolean isExitst = false;
		int SwiftNum = (int) account.getContainerCount();
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		for (int i = 0; i < SwiftNum; i++) {
			if (containers.get(i).getName().equals(srcLocation.getContainerName())) {

				System.out.println("�鵽�˸�����������IDΪ" + i);
				int objectNumber = containers.get(i).getObjectCount();
				List<? extends SwiftObject> objects = os.objectStorage().objects().list(srContainerName);
				for(int j = 0; j < objectNumber; j++){
					//System.out.println(objects.get(j).getName());
					if(objects.get(j).getName().equals(srObjectName))
						System.out.println("�鵽�˸ö��󣬶���IDΪ" + j);
						isExitst = true;
				}
				
			}
		}

		if (isExitst == false) {
			System.out.println("����������󲻴��ڣ�����������");
		}
		
		// Ŀ���ļ������ж�
		boolean isExitst1 = true;
		for (int i = 0; i < SwiftNum; i++) {
			if (containers.get(i).getName().equals(destLocation.getContainerName())) {

				System.out.println("�鵽�˸�����������IDΪ" + i);
				int objectNumber = containers.get(i).getObjectCount();
				List<? extends SwiftObject> objects = os.objectStorage().objects().list(desContainerName);
				for(int j = 0; j < objectNumber; j++){
					if(objects.get(j).getName().equals(srObjectName))
						System.out.println("�ö�����Ŀ���������Ѵ��ڣ�����IDΪ" + j+"�����¶���һ����������");
						isExitst1 = false;
						}
				}
		}

		// copy ����
		if((isExitst==true)&&(isExitst1==false)){
			System.out.println("�ö�����Ŀ�������в�����");
			System.out.println("ԭ��ַurl"+srcLocation.getURI()+"Ŀ�ĵ�ַurl"+destLocation.getURI());
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
